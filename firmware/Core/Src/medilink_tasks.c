#include "medilink_tasks.h"

#include "FreeRTOS.h"
#include "queue.h"
#include "task.h"

#include "medilink_config.h"
#include "medilink_hal.h"
#include "medilink_protocol.h"

#include <string.h>

#define MEDILINK_COMMAND_BUFFER_SIZE 64U
#define MEDILINK_JSON_BUFFER_SIZE    192U

static QueueHandle_t command_queue;
static medilink_snapshot_t snapshot;

static void sensor_task(void *argument);
static void mqtt_rx_task(void *argument);
static void command_task(void *argument);
static void upload_task(void *argument);
static void medicine_task(void *argument);

bool medilink_app_start(void)
{
    command_queue = xQueueCreate(8U, sizeof(medilink_command_t));
    if (command_queue == NULL) {
        return false;
    }

    if (xTaskCreate(sensor_task, "ml_sensor", 256U, NULL, 2U, NULL) != pdPASS) return false;
    if (xTaskCreate(mqtt_rx_task, "ml_rx", 384U, NULL, 3U, NULL) != pdPASS) return false;
    if (xTaskCreate(command_task, "ml_cmd", 256U, NULL, 3U, NULL) != pdPASS) return false;
    if (xTaskCreate(upload_task, "ml_upload", 384U, NULL, 2U, NULL) != pdPASS) return false;
    if (xTaskCreate(medicine_task, "ml_med", 256U, NULL, 2U, NULL) != pdPASS) return false;

    return true;
}

static void sensor_task(void *argument)
{
    (void)argument;

    for (;;) {
        float temperature = 0.0f;
        float humidity = 0.0f;
        float illumination = 0.0f;

        if (medilink_hal_read_temperature_humidity(&temperature, &humidity)) {
            taskENTER_CRITICAL();
            snapshot.temperature = temperature;
            snapshot.humidity = humidity;
            taskEXIT_CRITICAL();
        }

        if (medilink_hal_read_illumination(&illumination)) {
            taskENTER_CRITICAL();
            snapshot.illumination = illumination;
            taskEXIT_CRITICAL();
        }

        vTaskDelay(pdMS_TO_TICKS(MEDILINK_SENSOR_PERIOD_MS));
    }
}

static void mqtt_rx_task(void *argument)
{
    (void)argument;
    char payload[MEDILINK_COMMAND_BUFFER_SIZE];

    while (!medilink_hal_network_init()) {
        vTaskDelay(pdMS_TO_TICKS(1000U));
    }

    for (;;) {
        if (!medilink_hal_mqtt_connect()) {
            vTaskDelay(pdMS_TO_TICKS(1500U));
            continue;
        }

        if (!medilink_hal_mqtt_subscribe_commands()) {
            vTaskDelay(pdMS_TO_TICKS(1000U));
            continue;
        }

        for (;;) {
            memset(payload, 0, sizeof(payload));
            if (!medilink_hal_mqtt_receive(payload, sizeof(payload), 1000U)) {
                continue;
            }

            medilink_command_t command = medilink_parse_command(payload);
            if (command != MEDILINK_CMD_NONE) {
                (void)xQueueSend(command_queue, &command, 0U);
            }
        }
    }
}

static void command_task(void *argument)
{
    (void)argument;
    medilink_command_t command;

    for (;;) {
        if (xQueueReceive(command_queue, &command, portMAX_DELAY) != pdTRUE) {
            continue;
        }

        switch (command) {
            case MEDILINK_CMD_BEEP_ON:
                medilink_hal_set_buzzer(true);
                break;
            case MEDILINK_CMD_BEEP_OFF:
                medilink_hal_set_buzzer(false);
                break;
            case MEDILINK_CMD_FAN_ON:
                medilink_hal_set_fan(true);
                break;
            case MEDILINK_CMD_FAN_OFF:
                medilink_hal_set_fan(false);
                break;
            default:
                break;
        }
    }
}

static void upload_task(void *argument)
{
    (void)argument;
    char json[MEDILINK_JSON_BUFFER_SIZE];

    for (;;) {
        medilink_snapshot_t current;

        taskENTER_CRITICAL();
        current = snapshot;
        taskEXIT_CRITICAL();

        if (medilink_build_telemetry_json(&current, json, sizeof(json))) {
            (void)medilink_hal_mqtt_publish(json);
        }

        vTaskDelay(pdMS_TO_TICKS(MEDILINK_UPLOAD_PERIOD_MS));
    }
}

static void medicine_task(void *argument)
{
    (void)argument;
    bool previous = false;

    for (;;) {
        bool current = medilink_hal_read_medicine_trigger();

        if (current && !previous) {
            taskENTER_CRITICAL();
            snapshot.take_medicine = 1;
            taskEXIT_CRITICAL();

            medilink_hal_on_medicine_taken();
        } else if (!current) {
            taskENTER_CRITICAL();
            snapshot.take_medicine = 0;
            taskEXIT_CRITICAL();
        }

        previous = current;
        vTaskDelay(pdMS_TO_TICKS(MEDILINK_MEDICINE_PERIOD_MS));
    }
}
