# Mobile / Android 移动端

## Overview / 简介

**English:** This directory contains the native Android client for MediLink-IoT. It provides user login and registration, device monitoring, remote control, medication management, medication history, and feedback submission.

**中文：** 本目录为 MediLink-IoT 的原生 Android 客户端，提供用户登录注册、设备状态监测、远程控制、用药管理、服药记录以及意见反馈等功能。

## Main Technologies / 主要技术

- Android
- Java
- OkHttp
- Gson
- Material Components

## API Configuration / 接口配置

**English:** Set the backend address in Gradle properties.

**中文：** 在 Gradle 配置中设置后端服务地址。

```properties
MEDILINK_API_BASE_URL=http://192.168.1.100:8080/medilink/
```

**English:** When using the Android emulator, `http://10.0.2.2:8080/medilink/` points to the host computer.

**中文：** 使用 Android 模拟器时，`http://10.0.2.2:8080/medilink/` 可访问宿主机上的后端服务。

## Network Security / 网络安全

**English:** Debug builds allow clear-text HTTP for local-network testing. Release builds disable clear-text HTTP and should use HTTPS.

**中文：** Debug 版本允许局域网 HTTP 调试；Release 版本默认关闭明文 HTTP，正式部署时建议使用 HTTPS。
