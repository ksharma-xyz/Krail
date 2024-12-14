# KRAIL <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/7/74/Kotlin_Icon.png/1200px-Kotlin_Icon.png" height="30">  <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/3/31/Android_robot_head.svg/1100px-Android_robot_head.svg.png" height="30">  <img src="https://upload.wikimedia.org/wikipedia/commons/c/ca/IOS_logo.svg" height="30">

Making travel simple and fun.

Krail is a Compose Multiplatform application designed to provide a seamless user experience across
Android and iOS platforms. This project leverages modern libraries and frameworks to ensure a robust
and scalable architecture.

[![Krail App CI](https://github.com/ksharma-xyz/Krail/actions/workflows/build.yml/badge.svg)](https://github.com/ksharma-xyz/Krail/actions/workflows/build.yml)

## Architecture

Krail follows a modular architecture with a clear separation of concerns. The main modules include:

- **ComposeApp**: Contains the main entry point for the android application and has platform target
  implementations as well.
- **iosApp**: Contains the main entry point for the iOS application

## Libraries Used

### Dependency Injection

- **Koin**: Used for dependency injection to manage and inject dependencies efficiently.

### Network

- **Ktor**: A framework for building asynchronous servers and clients in connected systems using
  Kotlin.

### Database

- **SQLDelight**: A library for generating typesafe Kotlin APIs from SQL.

## Theming / Design System

**:taj** module is the design system for the Krail App.
It's built using the compose foundation APIs. For now, Material Design is not used but inspiration
has been taken from it.

## Getting Started

To build the project, follow these steps:

1. **Clone the repository**:
    ```sh
    git clone git@github.com:ksharma-xyz/Krail.git
    ```

2. **Open the project in Android Studio**:

- Open `build.gradle.kts` file in Android Studio.
- Perform Gradle Sync (should happen automatically).
- Add `NSW_TRIP_PLANNER_API_KEY` in local.properties file, you can get a developer key from
  [here](https://opendata.transport.nsw.gov.au/).

3. **Build and run the project**:

- Select the desired target (Android or iOS).
- Click on the `Run` button.

## Contributing

Welcoming contributions from the community. Please create a new issue or pick up an existing one.

## Download

[<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/7/78/Google_Play_Store_badge_EN.svg/1280px-Google_Play_Store_badge_EN.svg.png" alt="Download Krail App on Google Play Store" width="150"/>](https://play.google.com/store/apps/details?id=xyz.ksharma.krail)

[<img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQk7OywFCuNaXPnmdgEAmthr_XrNbzxmt6oUQ&s" alt="iOS Krail App on Apple App Store" width="150"/>](https://apps.apple.com/us/app/krail-app/id6738934832)

## License

```
Copyright 2024 Karan Sharma.

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

## Contact

https://krail.app
[hey@krail.app](mailto:hey@krail.app)
