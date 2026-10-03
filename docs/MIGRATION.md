# Integration Notes / 整合说明

The repository is normalized as a single monorepo so the mobile, backend and firmware layers can be developed and reviewed together.

## Main cleanups

- Consolidated the three ends into `mobile/`, `backend/` and `firmware/`.
- Removed IDE/build output from version control through a root `.gitignore`.
- Replaced hard-coded LAN addresses with a configurable Android API base URL.
- Replaced source-code database and OneNET credentials with local configuration examples.
- Restored real login/register API calls instead of directly entering the home screen.
- Removed duplicate Android activity declarations by maintaining one canonical manifest.
- Unified device commands in one mapping, including the previously missing `FAN_OFF → FANOFF` path.
- Device status now requests the three OneNET streams through one snapshot request instead of repeating the same query.
- Release Android builds disable clear-text HTTP.
- Firmware command parsing accepts both cloud payloads such as `FANOFF` and normalized API names such as `FAN_OFF`.

## Directory mapping

```text
mobile/    Android application
backend/   Java Servlet backend + MySQL schema
firmware/  STM32F1 + FreeRTOS application layer
docs/      API, architecture, deployment and security notes
```

The generated/vendor-specific STM32Cube files are intentionally separated from project logic. The `firmware/App` hardware abstraction hooks are the integration boundary for the board-specific DHT11, BH1750, ESP8266, buzzer, fan, RTC, LCD and medicine-box drivers.
