# System Architecture / 系统架构

MediLink-IoT follows a three-end architecture composed of the mobile client, backend service and embedded device.

## Data Flow

1. The Android client sends authenticated HTTP/JSON requests to the Java backend.
2. The backend persists users, medication data, feedback and medication history in MySQL.
3. Device telemetry and remote-control commands are exchanged through OneNET.
4. The STM32F1 device uses ESP8266 and MQTT to maintain cloud connectivity.
5. FreeRTOS separates network communication, sensor acquisition, command processing, telemetry upload and medication-state logic into independent tasks.

## Device Telemetry

The normalized telemetry model uses three primary streams:

- `temperature`
- `humidity`
- `take_medicine`

## Device Commands

Application-level commands are normalized as:

| API command | Device payload |
| --- | --- |
| `BEEP_ON` | `BEEPON` |
| `BEEP_OFF` | `BEEPOFF` |
| `FAN_ON` | `FANON` |
| `FAN_OFF` | `FANOFF` |

Keeping these mappings in one place avoids command strings being duplicated across the mobile, backend and firmware codebases.
