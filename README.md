# Simple MVVM

A simple Android MVVM pattern example and template.

[![Developer](https://img.shields.io/badge/Maintainer-ImaginativeShohag-green)](https://github.com/ImaginativeShohag)
[![Developer](https://img.shields.io/badge/-buy_me_a_coffee-gray?logo=buy-me-a-coffee)](https://www.buymeacoffee.com/ImShohag)

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
- Data pass and receive example between Activities
  - Pass data on `Activity` start (`startActivity()`)
  - Start `Activity` for result (`registerForActivityResult()`)
- Data pass and receive example between Fragments
  - Pass data between child `Fragment` using parent `ViewModel`
  - Pass data between child `Fragment` using `FragmentResultListener`
  - Pass data on `Fragment` transaction
- Simple Todo app (for beginner)
  - Single Activity approach
  - Used [Go REST](https://gorest.co.in) API
  - Offline support
- Splash Screen (Introduced in Android 12)

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

- [ ] OneSignal integration
- [ ] UI/Unit Testing
- [ ] Example of `LocationProviderUtilClient`

# Note

- For dependency version check I am currently
  using [Gradle Versions Plugin](https://github.com/ben-manes/gradle-versions-plugin#using-a-gradle-init-script)
  . I added this in the Gradle init script and can check versions using the following commend.

```bash
./gradlew dependencyUpdates
```

- The project using [spotless](https://github.com/diffplug/spotless/tree/main/plugin-gradle)
  with [klint](https://github.com/pinterest/ktlint). Apply spotless using the following command.

```bash
./gradlew spotlessApply
```

## Setup

### Go REST API Key

Open the `local.properties` in your project level directory, and then add the following code.
Replace `YOUR_API_KEY` with your [Go REST](https://gorest.co.in) API key.

```groovy
API_KEY=YOUR_API_KEY
```

# My Other Projects

- [Oops! No Internet!](https://github.com/ImaginativeShohag/Oops-No-Internet) - A simple no Internet dialog and snackbar, which will automatically appear and disappear based on Internet connectivity status.
- [Why Not! Image Carousel!](https://github.com/ImaginativeShohag/Why-Not-Image-Carousel) - An easy, super simple and customizable image carousel view for Android.
- [Why Not Compose!](https://github.com/ImaginativeShohag/Why-Not-Compose) - A collection of animations, compositions, UIs using Jetpack Compose. You can say Jetpack Compose cookbook or play-ground if you want!
- [Why Not SwiftUI!](https://github.com/ImaginativeShohag/Why-Not-SwiftUI) - A collection of Swift, SwiftUI and iOS goodies.

## Licence

```
Copyright 2023 Md. Mahmudul Hasan Shohagm

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```