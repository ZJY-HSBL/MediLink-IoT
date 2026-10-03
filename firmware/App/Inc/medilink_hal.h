#ifndef MEDILINK_HAL_H
#define MEDILINK_HAL_H

#include <stdbool.h>
#include <stddef.h>

bool medilink_hal_network_init(void);
bool medilink_hal_mqtt_connect(void);
bool medilink_hal_mqtt_subscribe_commands(void);
bool medilink_hal_mqtt_receive(char *buffer, size_t buffer_size, unsigned int timeout_ms);
bool medilink_hal_mqtt_publish(const char *payload);

bool medilink_hal_read_temperature_humidity(float *temperature, float *humidity);
bool medilink_hal_read_illumination(float *illumination);
bool medilink_hal_read_medicine_trigger(void);

void medilink_hal_set_buzzer(bool enabled);
void medilink_hal_set_fan(bool enabled);
void medilink_hal_on_medicine_taken(void);

#endif
