# Deployment Guide / 部署说明

## Backend

Requirements:

- JDK 17+
- Maven 3.9+
- MySQL 8+
- Tomcat 10+

Create the database using `backend/sql/schema.sql`, then create an application config:

```bash
cp backend/src/main/resources/application-example.properties \
   backend/src/main/resources/application.properties
```

Build:

```bash
cd backend
mvn clean package
```

Deploy `target/medilink.war` to Tomcat.

## Android

The API base URL is injected through Gradle:

```properties
MEDILINK_API_BASE_URL=http://192.168.1.100:8080/medilink/
```

The debug build permits clear-text HTTP for LAN development. Release builds disable clear-text traffic and should point to an HTTPS endpoint.

## Firmware

Copy the example configuration header:

```text
firmware/Core/Inc/medilink_config.example.h
        ↓
firmware/Core/Inc/medilink_config.h
```

Fill in Wi-Fi, MQTT/OneNET and device parameters locally. Do not commit the generated private configuration.
