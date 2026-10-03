# API Reference / API 接口说明

Base path / 基础路径：`/api`

## Authentication / 身份认证

**English:** Authenticated endpoints require a Bearer Token.

**中文：** 需要登录的接口必须携带 Bearer Token。

```http
Authorization: Bearer <token>
```

## 1. Register / 用户注册

### POST `/api/auth/register`

Request / 请求：

```json
{
  "username": "alice",
  "password": "example-password",
  "displayName": "Alice"
}
```

**English:** Creates a user and returns an authentication token.  
**中文：** 创建用户并返回登录 Token。

## 2. Login / 用户登录

### POST `/api/auth/login`

**English:** Verifies username and password and returns a token.  
**中文：** 校验用户名和密码，登录成功后返回 Token。

## 3. Medicine List / 用药列表

### GET `/api/medicines`

**English:** Returns the current user's medication list.  
**中文：** 获取当前用户的用药信息列表。

## 4. Add Medicine / 添加用药

### POST `/api/medicines`

```json
{
  "name": "Vitamin C",
  "dosage": "1 tablet",
  "scheduleTime": "08:00",
  "notes": "After breakfast"
}
```

**中文示例说明：**

- `name`：药品名称
- `dosage`：剂量
- `scheduleTime`：计划服药时间
- `notes`：备注

## 5. Device Status / 设备状态

### GET `/api/device/status`

Response / 返回：

```json
{
  "temperature": 24.6,
  "humidity": 51.2,
  "takeMedicine": 1
}
```

**English:** Returns the latest temperature, humidity and medication state.  
**中文：** 返回最新温度、湿度与服药状态。

## 6. Device Control / 设备控制

### POST `/api/device/control`

```json
{
  "command": "FAN_OFF"
}
```

Supported commands / 支持指令：

- `BEEP_ON` — Buzzer on / 开启蜂鸣器
- `BEEP_OFF` — Buzzer off / 关闭蜂鸣器
- `FAN_ON` — Fan on / 开启风扇
- `FAN_OFF` — Fan off / 关闭风扇

## 7. Medication History / 服药历史

### GET `/api/history`

**English:** Returns recent medication records for the current user.  
**中文：** 获取当前用户最近的服药记录。

## 8. Feedback / 意见反馈

### POST `/api/feedback`

```json
{
  "message": "Device reminder works normally."
}
```

**English:** Submits user feedback.  
**中文：** 提交用户反馈信息。
