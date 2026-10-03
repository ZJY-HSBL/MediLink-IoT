#ifndef MEDILINK_PROTOCOL_H
#define MEDILINK_PROTOCOL_H

#include <stdbool.h>
#include <stddef.h>

typedef enum {
    MEDILINK_CMD_NONE = 0,
    MEDILINK_CMD_BEEP_ON,
    MEDILINK_CMD_BEEP_OFF,
    MEDILINK_CMD_FAN_ON,
    MEDILINK_CMD_FAN_OFF
} medilink_command_t;

typedef struct {
    float temperature;
    float humidity;
    float illumination;
    int take_medicine;
} medilink_snapshot_t;

medilink_command_t medilink_parse_command(const char *payload);
bool medilink_build_telemetry_json(
        const medilink_snapshot_t *snapshot,
        char *buffer,
        size_t buffer_size);

#endif
