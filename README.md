![banner](https://i.morioh.com/201102/1b1fd3c8.webp)
# [Movie_Mvi_Compose](https://github.com/amirhusseinSSoori/Movie_Mvi_Compose)

## Introduction

  In this repository I have used network bound resources  with jetpack compose and Mvi architecture

  


Network bound resources

If the device is offline, any cached values should immediately be dispatched.
Otherwise, any cached values should be dispatched as part of a temporary loading state.
The algorithm then determines whether the cached values are sufficiently recent, or whether the data should be fetched from the network. If sufficiently recent, the cached values are dispatched.
Otherwise, the algorithm determines whether a login is necessary (for example, if an authentication token is missing or expired), and if so, attempts to reauthenticate.

If the login fails because the credentials are invalid, the user is prompted to re-enter the credentials, and the algorithm stops. Once the user has entered the credentials, the algorithm should be automatically restarted, without the user having to refresh.
The data is now fetched over the network.

If the call was unsuccessful, the reason is evaluated. If the call failed because it was unauthorized, it is necessary to reauthenticate and redo the call. Otherwise, the error should be dispatched.
If the call was successful, the data is saved, automatically triggering a refresh.

## Libraries used

* [Gson Converter](https://github.com/square/retrofit/tree/master/retrofit-converters/gson)
* [Retrofit](https://square.github.io/retrofit/) 
* [Room](https://developer.android.com/topic/libraries/architecture/room) 
* [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines) 
* [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines) 
* [Lotti](https://github.com/airbnb/lottie/blob/master/android-compose.md)
* [Navigating with Compose](https://developer.android.com/jetpack/compose/navigationd)  
* [Hilt and Jetpack integrations](https://developer.android.com/training/dependency-injection/hilt-jetpack)



<img src="/screenshots/movie.png" width="300" >  <img src="/screenshots/details.png" width="300" >
<img src="/screenshots/error.png" width="300" >






