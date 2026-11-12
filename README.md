# TicTacToe ❌⭕

A modern, real-time multiplayer Android application built with **Jetpack Compose**, **Material 3**, and **Firebase Realtime Database**, powered by a custom **State Machine** architecture.

---

## 🚀 Features

- **Real-Time Multiplayer Synchronization**: Synchronize game state, turns, scores (`GameStat`), winning phases, and game resets instantly across multiple devices using Firebase Realtime Database.
- **Modern Declarative UI**: Crafted entirely with **Jetpack Compose** and **Material 3** design principles, supporting dark/light themes.
- **Adaptive Layouts**: Responsive UI adapting smoothly to both portrait and landscape orientations using Local Configuration.
- **State Machine Architecture**: Predictable state management powered by Kotlin `StateFlow`, custom state reducers, and `rememberSaveable` persistence.
- **Robust Error-Free Parsing**: Safe bidirectional mapping between enum types (`Player`, `GamePhase`) and database string payloads.

---

## 🛠️ Tech Stack & Architecture

- **Language**: [Kotlin](https://kotlinlang.org/)
- **UI Toolkit**: [Jetpack Compose](https://developer.android.com/jetpack/compose) & [Material 3](https://m3.material.io/)
- **State Management**: Kotlin Coroutines & `StateFlow`, custom State Machine pattern
- **Backend / Sync**: [Firebase Realtime Database](https://firebase.google.com/products/realtime-database)
- **Lifecycle & Asynchronous**: Lifecycle Runtime Compose, `LaunchedEffect`, `ValueEventListener`, Local Configuration

---

## ⚙️ Architecture & Data Flow

1. **State Mutation**: User interactions dispatch events to the `TicTacToeMachine`, which produces a new immutable `TicTacToeUiState`.
2. **Reactive Side-Effects**: A `LaunchedEffect(ticTacToeUiState)` observes local state changes and automatically persists updates to Firebase Realtime Database.
3. **Cross-Device Sync**: Firebase listeners (`ValueEventListener`) stream remote `GameState` updates to connected clients, parsing string fields back into typed enums (`Player?`, `GamePhase`) and updating the local state machine.
