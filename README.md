## Movie App Compose 📒
This app is an Android app built using Android Native Library Jetpack Compose and Material 3 design guidelines. 
The app follows Google recommended best practices and architecture.

## Screenshot

|![splash](https://github.com/user-attachments/assets/b3fd68ff-10ee-473d-b82a-90160254711b)|![main](https://github.com/user-attachments/assets/82099f42-dc04-4849-9e73-96d35ed0c5b1)|![edit](https://github.com/user-attachments/assets/077a3045-731b-4c7d-94d2-77f37c1f6d44)|![delete](https://github.com/user-attachments/assets/77c0cfad-7c63-4863-aaa8-91cb23973f33)|
|----|----|----|----|
|![color](https://github.com/user-attachments/assets/49433124-36b2-462a-854f-5d8d0f22caeb)|![ascending](https://github.com/user-attachments/assets/02488edd-e288-40a0-a448-94a17a1d251e)|![add](https://github.com/user-attachments/assets/9c4d8fe4-18aa-4320-b397-0e6e75e9fb1d)|

## Tech Stack & Open Source Libraries
- Minimum SDK level 21
- 100% [Kotlin](https://kotlinlang.org/)  based + [Flow](https://developer.android.com/kotlin/flow) and [Coroutines](https://developer.android.com/kotlin/coroutines)
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

![Architecture](https://user-images.githubusercontent.com/21035435/69536839-9f4c8e80-0fa0-11ea-85ee-d7823e5a46b0.png)
