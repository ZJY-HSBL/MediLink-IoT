# API Reference

Base path: `/api`

All authenticated endpoints require:

```http
Authorization: Bearer <token>
```

## Authentication

### POST /api/auth/register

```json
{
  "username": "alice",
  "password": "example-password",
  "displayName": "Alice"
}
```

### POST /api/auth/login

Returns an authentication token.

## Medicines

### GET /api/medicines

Returns the current user's medication list.

### POST /api/medicines

```json
{
  "name": "Vitamin C",
  "dosage": "1 tablet",
  "scheduleTime": "08:00",
  "notes": "After breakfast"
}
```

## Device

### GET /api/device/status

Example response:

```json
{
  "temperature": 24.6,
  "humidity": 51.2,
  "takeMedicine": 1
}
```

### POST /api/device/control

```json
{
  "command": "FAN_OFF"
}
```

Supported commands: `BEEP_ON`, `BEEP_OFF`, `FAN_ON`, `FAN_OFF`.

## History

### GET /api/history

Returns recent medication records.

## Feedback

### POST /api/feedback

```json
{
  "message": "Device reminder works normally."
}
```
