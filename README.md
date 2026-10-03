# MediLink-IoT

> **Intelligent IoT medication management and family health monitoring system · 面向家庭场景的智能物联网用药管理与健康监测系统**

MediLink-IoT is a full-stack smart medication management platform that integrates an Android client, a Java Web backend, a MySQL database, OneNET IoT connectivity, and STM32/FreeRTOS firmware.

MediLink-IoT 是一个面向家庭场景的智能用药管理与健康监测平台，整合 Android 移动端、Java Web 后端、MySQL 数据库、OneNET 物联网连接以及 STM32/FreeRTOS 嵌入式固件，实现用药信息管理、服药状态记录、环境监测与远程设备控制。

## Architecture

```text
┌─────────────────────┐
│   Android Mobile    │
│ Java + OkHttp/Gson  │
└──────────┬──────────┘
           │ HTTP/JSON
           ▼
┌─────────────────────┐      ┌──────────────┐
│   Java Web Backend  │─────▶│    MySQL     │
│ Servlet + Maven     │      └──────────────┘
└──────────┬──────────┘
           │ OneNET REST API
           ▼
┌─────────────────────┐
│    OneNET Cloud     │
└──────────┬──────────┘
           │ MQTT / Wi-Fi
           ▼
┌─────────────────────┐
│ STM32F1 + FreeRTOS  │
│ ESP8266 + Sensors   │
└─────────────────────┘
```

## Repository Layout

```text
MediLink-IoT/
├── backend/          Java Web backend and database schema
├── mobile/           Android application
├── firmware/         STM32/FreeRTOS application layer
├── docs/             Architecture, API and deployment documentation
├── .gitignore
└── README.md
```

## Core Features

- User registration and token-based authentication
- Medication information management
- Medication-taking history
- Temperature and humidity monitoring
- Medicine-taking state synchronization
- Remote buzzer and fan control
- OneNET cloud integration
- STM32/FreeRTOS task-based embedded control
- Configurable server, database, Wi-Fi and IoT credentials

## Technology Stack

| Layer | Technologies |
| --- | --- |
| Mobile | Android, Java, OkHttp, Gson |
| Backend | Java 17, Jakarta Servlet, Maven |
| Database | MySQL 8 |
| IoT Cloud | OneNET |
| Firmware | STM32F1, FreeRTOS |
| Connectivity | ESP8266, MQTT |
| Sensors / Actuators | DHT11, BH1750, RTC, LCD, buzzer, fan, medicine-box actuator |

## Quick Start

1. Import `backend/sql/schema.sql` into MySQL.
2. Copy `backend/src/main/resources/application-example.properties` to `application.properties` and fill in your own values.
3. Build the backend with `mvn clean package` and deploy the WAR to Tomcat 10+.
4. Set `MEDILINK_API_BASE_URL` in Android Gradle properties or use the emulator default.
5. Copy `firmware/Core/Inc/medilink_config.example.h` to `medilink_config.h`, then integrate the application layer into the STM32CubeMX project.

## Configuration & Security

Real database passwords, OneNET API keys, Wi-Fi credentials and device identifiers are intentionally excluded from version control. Never commit production secrets. Release deployments should use HTTPS.

真实数据库密码、OneNET API Key、Wi-Fi 密码及设备标识不会提交到版本库。正式部署时请使用 HTTPS，并通过本地配置文件或环境变量注入敏感参数。

## Documentation

- [Architecture](docs/ARCHITECTURE.md)
- [API Reference](docs/API.md)
- [Deployment Guide](docs/DEPLOYMENT.md)
- [Security Notes](docs/SECURITY.md)
- [Integration Notes](docs/MIGRATION.md)
