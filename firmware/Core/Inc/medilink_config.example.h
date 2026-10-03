#ifndef MEDILINK_CONFIG_H
#define MEDILINK_CONFIG_H

#define MEDILINK_WIFI_SSID          "your_wifi_ssid"
#define MEDILINK_WIFI_PASSWORD      "your_wifi_password"

#define MEDILINK_MQTT_HOST          "your_mqtt_host"
#define MEDILINK_MQTT_PORT          1883
#define MEDILINK_MQTT_CLIENT_ID     "your_device_id"
#define MEDILINK_MQTT_USERNAME      "your_product_or_username"
#define MEDILINK_MQTT_PASSWORD      "your_token_or_password"

#define MEDILINK_TELEMETRY_TOPIC    "$sys/your_product/your_device/dp/post/json"
#define MEDILINK_COMMAND_TOPIC      "$sys/your_product/your_device/cmd/request/+"

#define MEDILINK_SENSOR_PERIOD_MS   2000U
#define MEDILINK_UPLOAD_PERIOD_MS   5000U
#define MEDILINK_MEDICINE_PERIOD_MS 250U

#endif
