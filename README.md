# Simple MVVM

A simple Android MVVM pattern example and template.

## Used Components/Libraries

- Kotlin
- Kotlin Coroutines (https://developer.android.com/kotlin/coroutines)
- View Binding (https://developer.android.com/topic/libraries/view-binding)
- Data Binding (https://developer.android.com/topic/libraries/data-binding)
- Retrofit (https://github.com/square/retrofit) with Moshi (https://github.com/square/moshi)
- Room Persistence Library  (https://developer.android.com/topic/libraries/architecture/room)
- Shared Preferences (https://developer.android.com/training/data-storage/shared-preferences)
- Navigation Component (https://developer.android.com/guide/navigation)
- Glide (https://github.com/bumptech/glide)
- Timber (https://github.com/JakeWharton/timber)
- Custom Fonts (https://developer.android.com/guide/topics/ui/look-and-feel/fonts-in-xml)
- Shimmer for Android (https://github.com/facebook/shimmer-android)
- Hilt (https://dagger.dev/hilt/)
- Paging 3 (https://developer.android.com/topic/libraries/architecture/paging)
- Dexter (https://github.com/Karumi/Dexter) **(coming soon...)**

## Others

- Custom Build variant (https://developer.android.com/studio/build/build-variants)
- Environment variable based on Build variant
- Custom Snackbar
- Dark Mode
- Awesome module naming example (see `AwesomeModuleConstants`)
- Dynamic accent color from image (see `calculatePaletteInImage()`)
- Simple Encryption Utils (see `EncryptionUtils`)

## Extension Functions (see `utils/extensions`)

- DateTime
- Dialog
- Dimension
- Flow
- General Spinner
- Miscellaneous: show/hide keyboard, download file, open permission settings, open url, send mail
  etc.
- Retrofit
- Snackbar
- String
- Toast

## Common Binding Adapters

- RecyclerView
- Spinner
- ImageView
- TextView (for Date() to formatted date-time)

# Utilities

- `SharedPref`
- `AlarmUtils`
- `LocationProviderUtilClient`
- `Event` Class: Get notified for identical values in LiveData, Channel, Flow etc.
- `ignoreCrash { }`: Ignore exceptions for a block.

## Todo

- [ ] Add New Post
- [ ] Demo Login
- [ ] Demo Registration
- [ ] OneSignal integration
- [ ] Splash (Introduced in Android S)
- [ ] UI/Unit Testing
- [ ] Example of LocationProviderUtilClient
- [ ] Data pass between activities and fragments (on going)

# My Other Projects

- [Oops! No Internet!](https://github.com/ImaginativeShohag/Oops-No-Internet) - A simple no Internet dialog and snackbar, which will automatically appear and disappear based on Internet connectivity status.
- [Why Not! Image Carousel!](https://github.com/ImaginativeShohag/Why-Not-Image-Carousel) - An easy, super simple and customizable image carousel view for Android.
- [Why Not Compose!](https://github.com/ImaginativeShohag/Why-Not-Compose) - A collection of animations, compositions, UIs using Jetpack Compose. You can say Jetpack Compose cookbook or play-ground if you want!