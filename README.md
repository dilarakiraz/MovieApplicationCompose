## Movie App Compose 
This app is an Android app built using Android Native Library Jetpack Compose and Material 3 design guidelines. 
The app follows Google recommended best practices and architecture.

## Screenshot

| **Main Screen** | **Detail Screen** | **Add to Favorites** |
|----|----|----|
| ![main](https://github.com/user-attachments/assets/2d1e7388-164f-4be3-bc3d-d4e9bd3909b1) | ![detail](https://github.com/user-attachments/assets/67c78169-3c86-4f11-aa23-38c4b4f4ee05) | ![addFav](https://github.com/user-attachments/assets/d14bd2f0-decc-48db-9f8d-62be4b7d1e64) |
| **Favorites Screen** | **Filter Screen** | **Search Screen** |
| ![favScreen](https://github.com/user-attachments/assets/99975cfa-ecab-46c1-8f86-70848eebfe73) | ![filterScreen](https://github.com/user-attachments/assets/d729b509-9c52-4752-a9e3-df93ee7a628c) | ![search](https://github.com/user-attachments/assets/cef1da6b-17a0-46bd-b246-0af6cade86bf) |

## Tech Stack & Open Source Libraries
- Minimum SDK level 21
- 100% [Kotlin](https://kotlinlang.org/) and [Coroutines](https://developer.android.com/kotlin/coroutines)
- [Architecture Components](https://developer.android.com/topic/libraries/architecture)
  - [Repository](https://developer.android.com/topic/architecture/data-layer) pattern is a design pattern that isolates the data layer from the rest of the app
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) class is a business logic or screen level state holder. It exposes the state to the UI and encapsulates related business logic
  - [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) is a class that holds the information about the lifecycle state of a component (like an activity or a fragment) and allows other objects to observe this state
- [Navigation Component](https://developer.android.com/guide/navigation) refers to the interactions that allow users to navigate across, into, and back out from the different pieces of content within the app
- [Retrofit](https://square.github.io/retrofit/) A type-safe HTTP client for Android and Java
- [Gson](https://github.com/google/gson) is a Java library for converting Java Objects into their JSON representation
- [Dagger Hilt](https://dagger.dev/hilt/) Hilt provides a standard way to incorporate Dagger dependency injection into an Android application
- [Coil](https://coil-kt.github.io/coil/) An image loading library for Android backed by Kotlin Coroutines
- [Room](https://developer.android.com/training/data-storage/room) persistence library provides an abstraction layer over SQLite, allowing for more robust database access while harnessing the full power of SQLite
- [Jetpack Datastore](https://developer.android.com/topic/libraries/architecture/datastore) is a data storage solution that stores key-value pairs or typed objects with protocol buffers
- [Jetpack Compose](https://developer.android.com/compose) is Android’s recommended modern toolkit for building native UI
- [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) helps you load and display pages of data from a larger dataset from local storage or over a network

## Architecture
MVVM [***Model View ViewModel***](https://developer.android.com/topic/architecture#recommended-app-arch)

![mvvm](https://github.com/user-attachments/assets/2dda4618-5d80-48db-8fcf-0a9546792aff)
