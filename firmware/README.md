# Firmware

STM32F1 + FreeRTOS application layer for MediLink-IoT.

The code in this directory isolates the project-specific logic from vendor-generated STM32CubeMX/HAL files. Integrate it into the existing STM32 project and bind the hardware hooks in `App/Src/medilink_hal.c` to the actual DHT11, BH1750, ESP8266/MQTT, buzzer, fan and medicine-box drivers.

## Tasks

- Sensor acquisition task
- MQTT receive task
- Device-command processing task
- Telemetry upload task
- Medication-state monitoring task

## Commands

| Payload | Action |
| --- | --- |
| `BEEPON` | Turn buzzer on |
| `BEEPOFF` | Turn buzzer off |
| `FANON` | Turn fan on |
| `FANOFF` | Turn fan off |

## Configuration

Copy:

```text
Core/Inc/medilink_config.example.h
        ↓
Core/Inc/medilink_config.h
```

Then set your own Wi-Fi, MQTT/OneNET and device values. The private configuration file is ignored by Git.
