# todometer-android

A faithful native rewrite of the [todometer Electron app](https://www.github.com/cassidoo/todometer) project here on Github.

## Libraries and Frameworks Used
- [Room](https://developer.android.com/topic/libraries/architecture/room): Storage of the task objects locally on-device
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata): Keeping the UI up to date with the state of the tasks in the database. This plays really well together with Room.
- [RxJava](https://github.com/ReactiveX/RxJava): Making it possible to chain database events easily in the background. Probably overkill for my purposes.
- [Dagger](https://github.com/google/dagger): I started the project with the intent of injecting everything but ended up only using it for the repository dependency.

&nbsp;

<img src="snapshot.png" width="300">

&nbsp;

## Releases

### 2.0 | October 2020
- Added 'Completed' section
- Made 'Do Later' and 'Completed' sections collapsible 
- Tweaked the behavior of the add todo text field to make quickly adding multiple items easier
### 1.0 | January 2020
- Initial release!
