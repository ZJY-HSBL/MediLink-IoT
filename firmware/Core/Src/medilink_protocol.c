#include "medilink_protocol.h"

#include <stdio.h>
#include <string.h>

medilink_command_t medilink_parse_command(const char *payload)
{
    if (payload == NULL) {
        return MEDILINK_CMD_NONE;
    }

    if (strcmp(payload, "BEEPON") == 0 || strcmp(payload, "BEEP_ON") == 0) {
        return MEDILINK_CMD_BEEP_ON;
    }
    if (strcmp(payload, "BEEPOFF") == 0 || strcmp(payload, "BEEP_OFF") == 0) {
        return MEDILINK_CMD_BEEP_OFF;
    }
    if (strcmp(payload, "FANON") == 0 || strcmp(payload, "FAN_ON") == 0) {
        return MEDILINK_CMD_FAN_ON;
    }
    if (strcmp(payload, "FANOFF") == 0 || strcmp(payload, "FAN_OFF") == 0) {
        return MEDILINK_CMD_FAN_OFF;
    }
    return MEDILINK_CMD_NONE;
}

bool medilink_build_telemetry_json(
        const medilink_snapshot_t *snapshot,
        char *buffer,
        size_t buffer_size)
{
    if (snapshot == NULL || buffer == NULL || buffer_size == 0U) {
        return false;
    }

    int written = snprintf(
            buffer,
            buffer_size,
            "{\"temperature\":%.2f,\"humidity\":%.2f,"
            "\"illumination\":%.2f,\"take_medicine\":%d}",
            (double)snapshot->temperature,
            (double)snapshot->humidity,
            (double)snapshot->illumination,
            snapshot->take_medicine);

    return written > 0 && (size_t)written < buffer_size;
}
