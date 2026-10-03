# Security Notes

The repository is designed to avoid committing secrets.

- Database credentials are read from configuration or environment variables.
- OneNET API credentials and device identifiers stay outside source control.
- Wi-Fi credentials are kept in `medilink_config.h`, which is ignored by Git.
- Passwords are stored as BCrypt hashes.
- Backend authentication uses expiring bearer tokens.
- Android release builds disable clear-text HTTP.
- API responses do not return database passwords or IoT credentials.

For production deployment, place the backend behind a reverse proxy with TLS and rotate any credentials that were previously committed to another repository or shared publicly.
