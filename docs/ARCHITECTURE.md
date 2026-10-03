# System Architecture / 系统架构

## Overview / 概述

**English:** MediLink-IoT uses a three-end architecture composed of an Android mobile client, a Java Web backend, and an STM32/FreeRTOS embedded device.

**中文：** MediLink-IoT 采用移动端、后端与嵌入式设备三端协同架构，由 Android 客户端、Java Web 后端以及 STM32/FreeRTOS 硬件端组成。

## Data Flow / 数据流

1. **Mobile → Backend / 移动端 → 后端**  
   English: The Android client sends HTTP/JSON requests to the backend.  
   中文：Android 客户端通过 HTTP/JSON 调用后端接口。

2. **Backend → MySQL / 后端 → 数据库**  
   English: User information, medication data, medication history and feedback are stored in MySQL.  
   中文：用户信息、用药数据、服药历史和反馈信息保存至 MySQL。

3. **Backend → OneNET / 后端 → OneNET**  
   English: The backend queries telemetry and sends device-control commands through OneNET.  
   中文：后端通过 OneNET 查询设备遥测数据并发送设备控制指令。

4. **OneNET → STM32 / OneNET → STM32**  
   English: ESP8266 and MQTT provide cloud-device connectivity.  
   中文：硬件端通过 ESP8266 与 MQTT 建立设备和云平台之间的通信。

5. **FreeRTOS Task Scheduling / FreeRTOS 任务调度**  
   English: Sensor acquisition, MQTT receiving, command processing, telemetry upload and medication detection run as separate tasks.  
   中文：传感器采集、MQTT 接收、指令处理、数据上传和服药检测分别运行在独立 FreeRTOS 任务中。

## Device Telemetry / 设备遥测数据

| Stream / 数据流 | Description / 说明 |
| --- | --- |
| `temperature` | Temperature / 温度 |
| `humidity` | Humidity / 湿度 |
| `take_medicine` | Medication-taking state / 服药状态 |

## Device Commands / 设备控制指令

| API Command / API 指令 | Device Payload / 设备载荷 | Description / 说明 |
| --- | --- | --- |
| `BEEP_ON` | `BEEPON` | Buzzer on / 开启蜂鸣器 |
| `BEEP_OFF` | `BEEPOFF` | Buzzer off / 关闭蜂鸣器 |
| `FAN_ON` | `FANON` | Fan on / 开启风扇 |
| `FAN_OFF` | `FANOFF` | Fan off / 关闭风扇 |

**English:** Centralizing these mappings prevents command strings from being duplicated inconsistently across the three ends.

**中文：** 将控制指令统一映射，可以避免移动端、后端和硬件端分别维护字符串时出现不一致问题。
