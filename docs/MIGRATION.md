# Integration Notes / 项目整合说明

## Purpose / 整合目的

**English:** The original system consisted of separate mobile, backend and embedded projects. This repository reorganizes them into one monorepo so that the complete software-hardware system can be developed, reviewed and demonstrated together.

**中文：** 原项目由移动端、后端和嵌入式硬件端分别维护。本仓库将三端统一整理为一个 Monorepo，便于完整展示、协同开发、代码审查与后续维护。

## Main Improvements / 主要改进

| Improvement / 改进项 | Description / 说明 |
| --- | --- |
| Unified repository / 统一仓库 | Merged into `mobile/`, `backend/`, `firmware/` |
| Build cleanup / 构建清理 | Excluded `build/`, `target/`, `.idea/` and generated files |
| API configuration / 接口配置化 | Removed scattered hard-coded LAN IP addresses |
| Secret management / 敏感信息管理 | Replaced database, OneNET, Wi-Fi and MQTT secrets with examples |
| Login flow / 登录流程 | Restored real backend authentication instead of directly entering the home page |
| Android manifest / Android 清单 | Removed duplicate activity declarations |
| Device control / 设备控制 | Unified `BEEP_ON`, `BEEP_OFF`, `FAN_ON`, `FAN_OFF` mappings |
| OneNET queries / OneNET 查询 | Consolidated device status queries into a single snapshot request |
| HTTP security / HTTP 安全 | Release Android builds disable clear-text HTTP |
| Firmware protocol / 固件协议 | Firmware accepts normalized API commands and device payload commands |

## Directory Mapping / 目录对应关系

```text
mobile/    Android application / Android 移动端
backend/   Java Servlet backend + MySQL / Java 后端与数据库
firmware/  STM32F1 + FreeRTOS application layer / 嵌入式应用层
docs/      Documentation / 项目文档
```

## Firmware Integration Boundary / 固件集成边界

**English:** Vendor-generated STM32Cube files and board-specific drivers are separated from project application logic. The `firmware/App` hardware abstraction functions provide the integration boundary for DHT11, BH1750, ESP8266, buzzer, fan, RTC, LCD and medicine-box drivers.

**中文：** STM32Cube 自动生成代码与具体板级驱动和项目应用逻辑分离。通过 `firmware/App` 中的硬件抽象接口，可以将 DHT11、BH1750、ESP8266、蜂鸣器、风扇、RTC、LCD 以及药箱执行机构等实际驱动接入统一应用层。
