# Dummy Player Android App

This is a modern Android frontend for the distributed Java gambling system. It connects to the Master server via TCP sockets and supports the dummy player workflow.

## Features
- **Search Games**: Filter by minimum stars, risk level, and bet category.
- **Play a Bet**: Place bets on specific games and view detailed results (payout, net profit/loss, jackpot status).
- **Rate Game**: Submit star ratings for any game.

## Architecture
- **Language**: 100% Java.
- **UI**: XML Layouts with Material Components.
- **Networking**: Raw Java Sockets (`ObjectOutputStream` / `ObjectInputStream`) for compatibility with the backend serialization.
- **State Management**: `ViewModel` + `LiveData` for a clean separation of concerns.

## Setup & Configuration

### Backend Connection
By default, the app is configured to connect to `10.0.2.2:12345` (standard Android Emulator address for `localhost`).

To change the host and port:
1. Open `SearchActivity.java`, `PlayActivity.java`, and `RateActivity.java`.
2. Locate the `GameRepository` initialization:
   ```java
   GameRepository repository = new GameRepository("10.0.2.2", 12345);
   ```
3. Update the IP address and port to match your Master server.

### Running the App
1. Ensure the backend Master server is running and accessible.
2. Build the project in Android Studio.
3. Run on an emulator or a real device (ensure the device can reach the server IP).

## Important Notes
- **Serialization**: The app uses standard Java serialization. Ensure the backend classes match the field names in the `com.example.myapplication.model` package.
- **Permissions**: Internet permission is already added to `AndroidManifest.xml`.
