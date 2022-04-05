# Chords 🎶
A small utility library to make Jetbrains/Jetpack Compose more harmonic.

## Setup 🛠
...

## Features ⭐️
### Dynamic Theme 🎨
Supports system dark mode and [Material You Dynamic Color](https://m3.material.io/styles/color/dynamic-color/overview) out of the box while allowing for fallback themes used in environments where dynamic color is not supported (such as older Android versions or desktop targets)

*Example*
```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent(lightScheme = myLightColorScheme, darkScheme = myDarkColorScheme) {
            DynamicTheme {
                MyComposeView()
            }
        }
    }
}
```

## Project ⚙️
### Versioning 📜
This project follows the [Semver](https://semver.org) standard for semantic versioning.

### License 📃
This project is licensed under [Apache 2.0](http://www.apache.org/licenses/LICENSE-2.0)

```
Copyright 2022 Antonius Naumann

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