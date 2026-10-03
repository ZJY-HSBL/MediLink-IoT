# Security Notes / 安全说明

## Credential Protection / 凭据保护

**English:** The repository is designed to avoid committing real credentials.

**中文：** 本仓库按照“不提交真实敏感凭据”的原则进行整理。

- Database passwords are loaded from local configuration or environment variables.  
  数据库密码通过本地配置文件或环境变量加载。

- OneNET API keys and device identifiers are excluded from source control.  
  OneNET API Key 与设备标识不写入公开源码。

- Wi-Fi credentials are kept in `medilink_config.h`, which is ignored by Git.  
  Wi-Fi 信息保存在被 Git 忽略的 `medilink_config.h` 中。

- User passwords are stored as BCrypt hashes.  
  用户密码使用 BCrypt 哈希后保存。

- Backend authentication uses expiring bearer tokens.  
  后端使用带有效期的 Bearer Token 进行认证。

- Android release builds disable clear-text HTTP.  
  Android Release 版本关闭明文 HTTP。

## Production Recommendations / 正式部署建议

**English**

Use HTTPS, place the backend behind a reverse proxy, restrict database exposure, rotate credentials regularly, and never reuse any secrets that have previously been published.

**中文**

正式部署时建议使用 HTTPS，将后端置于 Nginx 等反向代理之后，限制数据库公网暴露，定期轮换密钥，并且不要继续使用任何曾经公开过的密码或 API Key。
