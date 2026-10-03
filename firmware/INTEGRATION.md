# STM32 Integration

This repository keeps application logic separate from CubeMX-generated files.

## 1. Copy application sources

Add these files to the existing STM32 project:

- `Core/Src/medilink_protocol.c`
- `Core/Src/medilink_tasks.c`
- `App/Src/medilink_hal.c`

Add the corresponding include directories.

## 2. Bind hardware hooks

Provide strong implementations for the functions declared in `App/Inc/medilink_hal.h`.

Typical bindings:

- DHT11 → `medilink_hal_read_temperature_humidity`
- BH1750 → `medilink_hal_read_illumination`
- GPIO/PWM buzzer → `medilink_hal_set_buzzer`
- Fan GPIO/PWM → `medilink_hal_set_fan`
- ESP8266 + MQTT → network and MQTT hooks
- Medicine-box sensor/servo logic → medicine trigger hook

## 3. Start the application

After HAL initialization and before `vTaskStartScheduler()`, call:

```c
if (!medilink_app_start()) {
    Error_Handler();
}
```

## 4. Credentials

Keep `medilink_config.h` local. Only the example configuration belongs in Git.
