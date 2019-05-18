Cue dot
===================

This project was made for studies purposes, it's focuses on Clean Architecture approach. It's separated by modules, the `core` module is been written in kotlin and has no framework or platform dependencies, the `app` module uses Android framework dependencies.

The app uses MVVM pattern, [RxJava][0], [Koin][1], [LiveData][2], [ViewModel][3], [Lifecycles][4]

[0]: https://github.com/ReactiveX/RxJava
[1]: https://github.com/InsertKoinIO/koin
[2]: https://developer.android.com/topic/libraries/architecture/livedata
[3]: https://developer.android.com/topic/libraries/architecture/viewmodel
[4]: https://developer.android.com/topic/libraries/architecture/lifecycle

Upcoming features
-----------------
* List of favorite movies keeping in local storage (problably using [Room](https://developer.android.com/topic/libraries/architecture/room))
* Search movies by it's name

* Improve layout
* Android TV version

Runing this app
------------------
To run this project make sure to add your The Movie DB API key on `apiKey="YOUR_KEY"` in `local.properties` file

Third Party Content
-------------------
This project uses [The Movie DB API](https://www.themoviedb.org)
