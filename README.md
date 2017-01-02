chroma
======
[![Download](https://api.bintray.com/packages/itspriyesh/maven/chroma/images/download.svg)](https://bintray.com/itspriyesh/maven/chroma/_latestVersion)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-chroma-green.svg?style=true)](https://android-arsenal.com/details/1/3339)

A simple, lightweight color picker for Android written in Kotlin (interoperable with Java).

<img src="https://raw.githubusercontent.com/ItsPriyesh/chroma/master/art/red.png" width="250">
<img src="https://raw.githubusercontent.com/ItsPriyesh/chroma/master/art/green.png" width="250">
<img src="https://raw.githubusercontent.com/ItsPriyesh/chroma/master/art/blue.png" width="250">

Download
--------
Add the following dependency to your projects `build.gradle`:
``` groovy
compile 'me.priyesh:chroma:1.0.2'
```

Usage
-----
To display a color picker `DialogFragment`:

``` java
new ChromaDialog.Builder()
    .initialColor(Color.GREEN)
    .colorMode(ColorMode.RGB) // There's also ARGB and HSV
    .onColorSelected(color -> /* do your stuff */)
    .positiveButtonText("Select") // "OK" by default
    .negativeButtonText("Exit") // "Cancel" by default
    .create()
    .show(getSupportFragmentManager(), "ChromaDialog");
```

Check out the [sample project](chroma-sample) for more details.

License
-------
    Copyright 2016 Priyesh Patel

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
