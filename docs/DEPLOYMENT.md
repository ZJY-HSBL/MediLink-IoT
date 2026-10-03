# Deployment Guide / 部署说明

## Backend / 后端部署

### Requirements / 环境要求

- JDK 17+
- Maven 3.9+
- MySQL 8+
- Tomcat 10+

### Database / 数据库

**English:** Import `backend/sql/schema.sql` into MySQL.

**中文：** 将 `backend/sql/schema.sql` 导入 MySQL 完成数据库初始化。

### Configuration / 配置文件

```bash
cp backend/src/main/resources/application-example.properties \
   backend/src/main/resources/application.properties
```

**English:** Fill in your own database and OneNET configuration.  
**中文：** 在新文件中填写自己的数据库账号、密码以及 OneNET 参数。

### Build / 构建

```bash
cd backend
mvn clean package
```

**English:** Deploy `target/medilink.war` to Tomcat.  
**中文：** 将生成的 `target/medilink.war` 部署到 Tomcat。

## Android / Android 部署

**English:** Configure the API base URL through Gradle properties.  
**中文：** 通过 Gradle 属性配置后端接口地址。

```properties
MEDILINK_API_BASE_URL=http://192.168.1.100:8080/medilink/
```

**English:** Debug builds allow HTTP for LAN testing, while release builds should use HTTPS.  
**中文：** Debug 版本允许局域网 HTTP 调试，Release 版本建议连接 HTTPS 服务。

## Firmware / 固件部署

**English:** Copy the example configuration header and fill in Wi-Fi, MQTT and device parameters.

**中文：** 复制示例配置头文件，并填写 Wi-Fi、MQTT 与设备参数。

```text
firmware/Core/Inc/medilink_config.example.h
        ↓
firmware/Core/Inc/medilink_config.h
```

**English:** Integrate the application layer with your existing STM32CubeMX project and provide the actual hardware-driver implementations.

**中文：** 将应用层代码集成到现有 STM32CubeMX 工程中，并根据实际硬件实现传感器、ESP8266、蜂鸣器、风扇等底层驱动接口。
