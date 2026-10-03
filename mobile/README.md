# Mobile

Native Android client for MediLink-IoT.

## Configuration

Set the backend URL in your global or project Gradle properties:

```properties
MEDILINK_API_BASE_URL=http://192.168.1.100:8080/medilink/
```

For the Android emulator, the default `http://10.0.2.2:8080/medilink/` points to the host machine.

Debug builds allow clear-text HTTP for local-network development. Release builds disable clear-text traffic.
