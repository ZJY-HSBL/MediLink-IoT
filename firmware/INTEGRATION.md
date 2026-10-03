# STM32 Integration / STM32 集成说明

## 1. Add Application Files / 添加应用层文件

**English:** Add the following files to the existing STM32CubeMX project.

**中文：** 将以下文件加入现有 STM32CubeMX 工程。

- `Core/Src/medilink_protocol.c`
- `Core/Src/medilink_tasks.c`
- `App/Src/medilink_hal.c`

Also add the corresponding include directories.  
同时请将对应头文件目录加入编译器包含路径。

## 2. Bind Hardware Drivers / 绑定硬件驱动

**English:** Implement the functions declared in `App/Inc/medilink_hal.h` using the actual board drivers.

**中文：** 根据实际硬件工程，对 `App/Inc/medilink_hal.h` 中声明的接口进行实现。

Typical mappings / 典型映射关系：

- DHT11 → temperature and humidity / 温湿度
- BH1750 → illumination / 光照
- GPIO or PWM → buzzer / 蜂鸣器
- GPIO or PWM → fan / 风扇
- ESP8266 + MQTT → network communication / 网络通信
- Medicine-box sensor or servo → medicine state / 药箱状态

## 3. Start FreeRTOS Application / 启动 FreeRTOS 应用

**English:** After HAL initialization and before `vTaskStartScheduler()`, call:

**中文：** 在 HAL 初始化完成后、`vTaskStartScheduler()` 之前调用：

```c
if (!medilink_app_start()) {
    Error_Handler();
}
```

## 4. Credentials / 凭据管理

**English:** Keep `medilink_config.h` local and do not commit real credentials.

**中文：** `medilink_config.h` 应仅保存在本地，不要将真实 Wi-Fi、MQTT 或 OneNET 凭据提交到 GitHub。
