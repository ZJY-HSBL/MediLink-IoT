# Backend / 后端

## Overview / 简介

**English:** This directory contains the Java Web backend of MediLink-IoT. It provides authentication, medication management, medication history, feedback, device status queries, and remote device-control APIs.

**中文：** 本目录为 MediLink-IoT 的 Java Web 后端，负责用户认证、用药管理、服药历史、反馈管理、设备状态查询以及远程设备控制等接口。

## Technology Stack / 技术栈

- Java 17
- Jakarta Servlet
- Maven
- MySQL 8
- Gson
- BCrypt

## Requirements / 环境要求

- JDK 17+
- Maven 3.9+
- MySQL 8+
- Tomcat 10+

## Configuration / 配置

**English:** Copy `application-example.properties` to `application.properties` and fill in your own database and OneNET configuration.

**中文：** 将 `application-example.properties` 复制为 `application.properties`，并填写自己的数据库与 OneNET 参数。

## Build / 构建

```bash
mvn clean package
```

**English:** The generated WAR file is `target/medilink.war`.

**中文：** 构建完成后会生成 `target/medilink.war`，可部署至 Tomcat。
