# MediLink-IoT

> **Intelligent IoT medication management and family health monitoring system · 面向家庭场景的智能物联网用药管理与健康监测系统**

## Project Overview / 项目简介

**English**

MediLink-IoT is a full-stack smart medication management platform that integrates an Android mobile client, a Java Web backend, a MySQL database, OneNET IoT connectivity, and STM32/FreeRTOS embedded firmware. The system is designed for home medication assistance and supports medication management, reminder interaction, medication-taking records, environmental monitoring, remote device control, and cloud-device communication.

**中文**

MediLink-IoT 是一个面向家庭场景的全栈智能用药管理平台，整合 Android 移动端、Java Web 后端、MySQL 数据库、OneNET 物联网连接以及 STM32/FreeRTOS 嵌入式固件。系统主要用于家庭用药辅助，可实现用药信息管理、提醒交互、服药记录、环境监测、远程设备控制以及云端与硬件设备之间的数据通信。

## System Architecture / 系统架构

```text
┌──────────────────────────┐
│ Android Mobile / 移动端   │
│ Java + OkHttp + Gson     │
└────────────┬─────────────┘
             │ HTTP / JSON
             ▼
┌──────────────────────────┐        ┌──────────────────┐
│ Java Web Backend / 后端   │───────▶│ MySQL Database   │
│ Servlet + Maven          │        │ MySQL 数据库      │
└────────────┬─────────────┘        └──────────────────┘
             │ OneNET REST API
             ▼
┌──────────────────────────┐
│ OneNET IoT Cloud         │
│ OneNET 物联网云平台       │
└────────────┬─────────────┘
             │ MQTT / Wi-Fi
             ▼
┌──────────────────────────┐
│ STM32F1 + FreeRTOS       │
│ ESP8266 + Sensors        │
│ 嵌入式硬件与传感器        │
└──────────────────────────┘
```

## Repository Structure / 仓库结构

```text
MediLink-IoT/
├── backend/          # Java Web backend / Java Web 后端
├── mobile/           # Android application / Android 移动端
├── firmware/         # STM32 + FreeRTOS firmware / 嵌入式固件
├── docs/             # Project documentation / 项目文档
├── .github/          # CI workflows / 持续集成工作流
├── .gitignore
└── README.md
```

## Core Features / 核心功能

| English | 中文 |
| --- | --- |
| User registration and authentication | 用户注册与身份认证 |
| Medication information management | 用药信息管理 |
| Medication-taking history | 服药历史记录 |
| Temperature and humidity monitoring | 温湿度环境监测 |
| Medication-taking status synchronization | 服药状态同步 |
| Remote buzzer and fan control | 蜂鸣器与风扇远程控制 |
| OneNET cloud integration | OneNET 云平台接入 |
| STM32/FreeRTOS task-based control | STM32/FreeRTOS 多任务控制 |
| Configurable database, server and IoT parameters | 数据库、服务器与物联网参数配置化 |

## Technology Stack / 技术栈

| Layer / 层级 | Technologies / 技术 |
| --- | --- |
| Mobile / 移动端 | Android, Java, OkHttp, Gson |
| Backend / 后端 | Java 17, Jakarta Servlet, Maven |
| Database / 数据库 | MySQL 8 |
| IoT Cloud / 物联网云 | OneNET |
| Firmware / 固件 | STM32F1, FreeRTOS |
| Connectivity / 通信 | ESP8266, MQTT |
| Sensors & Actuators / 传感器与执行器 | DHT11, BH1750, RTC, LCD, buzzer, fan |

## Quick Start / 快速开始

### 1. Database / 数据库

**English:** Import `backend/sql/schema.sql` into MySQL.

**中文：** 将 `backend/sql/schema.sql` 导入 MySQL，初始化项目数据库。

### 2. Backend / 后端

**English:** Copy the example configuration file and fill in your own database and OneNET parameters.

**中文：** 复制示例配置文件，并填写自己的数据库与 OneNET 参数。

```bash
cp backend/src/main/resources/application-example.properties \
   backend/src/main/resources/application.properties
```

Build the backend / 构建后端：

```bash
cd backend
mvn clean package
```

### 3. Android Mobile / Android 移动端

**English:** Configure `MEDILINK_API_BASE_URL` in Gradle properties.

**中文：** 在 Gradle 配置中设置后端接口地址 `MEDILINK_API_BASE_URL`。

```properties
MEDILINK_API_BASE_URL=http://192.168.1.100:8080/medilink/
```

### 4. Firmware / 嵌入式固件

**English:** Copy the firmware example configuration and fill in Wi-Fi, MQTT, and OneNET information.

**中文：** 复制固件示例配置文件，并填写 Wi-Fi、MQTT 与 OneNET 相关参数。

```text
firmware/Core/Inc/medilink_config.example.h
        ↓
firmware/Core/Inc/medilink_config.h
```

## Configuration & Security / 配置与安全

**English**

Real database passwords, OneNET API keys, Wi-Fi passwords, MQTT credentials, and device identifiers are intentionally excluded from version control. Production deployments should use HTTPS and private configuration files or environment variables.

**中文**

真实数据库密码、OneNET API Key、Wi-Fi 密码、MQTT 凭据以及设备标识不会提交到版本库。正式部署时建议使用 HTTPS，并通过本地配置文件或环境变量注入敏感参数。

## Documentation / 项目文档

- [Architecture / 系统架构](docs/ARCHITECTURE.md)
- [API Reference / 接口说明](docs/API.md)
- [Deployment Guide / 部署说明](docs/DEPLOYMENT.md)
- [Security Notes / 安全说明](docs/SECURITY.md)
- [Integration Notes / 整合说明](docs/MIGRATION.md)

## Project Positioning / 项目定位

**English:** This repository is intended for embedded IoT engineering practice, full-stack integration, smart medication management research prototypes, and academic project demonstrations.

**中文：** 本仓库可用于嵌入式物联网工程实践、软硬件全栈集成、智能用药管理原型研究以及课程设计或科研项目展示。
