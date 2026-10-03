# Firmware / 嵌入式固件

## Overview / 简介

**English:** This directory contains the STM32F1 + FreeRTOS application layer for MediLink-IoT. It handles sensor acquisition, MQTT communication, device-command processing, telemetry upload, and medication-state detection.

**中文：** 本目录为 MediLink-IoT 的 STM32F1 + FreeRTOS 嵌入式应用层，负责传感器采集、MQTT 通信、设备控制指令处理、遥测数据上传以及服药状态检测。

## Main Tasks / 主要任务

| Task / 任务 | Description / 说明 |
| --- | --- |
| Sensor task / 传感器任务 | Reads temperature, humidity and illumination / 采集温度、湿度和光照数据 |
| MQTT receive task / MQTT 接收任务 | Receives cloud commands / 接收云端控制指令 |
| Command task / 指令任务 | Controls buzzer and fan / 控制蜂鸣器与风扇 |
| Upload task / 上传任务 | Uploads telemetry to cloud / 上传设备遥测数据 |
| Medication task / 服药检测任务 | Detects medicine-box state / 检测药箱与服药状态 |

## Device Commands / 设备指令

| Payload / 指令 | Action / 功能 |
| --- | --- |
| `BEEPON` | Turn buzzer on / 开启蜂鸣器 |
| `BEEPOFF` | Turn buzzer off / 关闭蜂鸣器 |
| `FANON` | Turn fan on / 开启风扇 |
| `FANOFF` | Turn fan off / 关闭风扇 |

## Configuration / 配置

**English:** Copy the example header and configure Wi-Fi, MQTT, OneNET and device parameters locally.

**中文：** 复制示例配置头文件，并在本地填写 Wi-Fi、MQTT、OneNET 与设备相关参数。

```text
Core/Inc/medilink_config.example.h
        ↓
Core/Inc/medilink_config.h
```

**English:** The real configuration file is ignored by Git to avoid leaking secrets.

**中文：** 实际配置文件已加入 Git 忽略规则，避免 Wi-Fi 密码、设备密钥等敏感信息泄露。
