# Call Reminder App

This is a simple Android app that lets the user schedule call reminders.

## Features
- Pick a contact and schedule a time to call.
- Reminders are stored in an SQLite database.
- Notifications are triggered using `AlarmManager` and `NotificationManager`.
- View upcoming reminders and mark them as completed.
- View completed reminders in a separate screen.

## Build
Requires Android SDK and Gradle. Run:

```bash
./gradlew assembleDebug
```

If dependencies fail to download due to lack of network access, configure the environment with offline repositories.
