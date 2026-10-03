#include "medilink_hal.h"

/*
 * Replace these weak hooks with the project's concrete STM32/ESP8266/
 * sensor and actuator drivers. Keeping the application layer behind
 * these hooks prevents network credentials and board-specific code from
 * spreading across FreeRTOS tasks.
 */

#if defined(__GNUC__)
#define MEDILINK_WEAK __attribute__((weak))
#else
#define MEDILINK_WEAK
#endif

MEDILINK_WEAK bool medilink_hal_network_init(void) { return false; }
MEDILINK_WEAK bool medilink_hal_mqtt_connect(void) { return false; }
MEDILINK_WEAK bool medilink_hal_mqtt_subscribe_commands(void) { return false; }

MEDILINK_WEAK bool medilink_hal_mqtt_receive(
        char *buffer,
        size_t buffer_size,
        unsigned int timeout_ms)
{
    (void)buffer;
    (void)buffer_size;
    (void)timeout_ms;
    return false;
}

MEDILINK_WEAK bool medilink_hal_mqtt_publish(const char *payload)
{
    (void)payload;
    return false;
}

MEDILINK_WEAK bool medilink_hal_read_temperature_humidity(
        float *temperature,
        float *humidity)
{
    (void)temperature;
    (void)humidity;
    return false;
}

MEDILINK_WEAK bool medilink_hal_read_illumination(float *illumination)
{
    (void)illumination;
    return false;
}

MEDILINK_WEAK bool medilink_hal_read_medicine_trigger(void) { return false; }

MEDILINK_WEAK void medilink_hal_set_buzzer(bool enabled) { (void)enabled; }
MEDILINK_WEAK void medilink_hal_set_fan(bool enabled) { (void)enabled; }
MEDILINK_WEAK void medilink_hal_on_medicine_taken(void) {}
