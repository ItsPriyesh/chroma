chroma
======
A Material color picker view for Android written in Kotlin.

<img src="https://raw.githubusercontent.com/ItsPriyesh/chroma/master/art/red.png" width="250">
<img src="https://raw.githubusercontent.com/ItsPriyesh/chroma/master/art/green.png" width="250">
<img src="https://raw.githubusercontent.com/ItsPriyesh/chroma/master/art/blue.png" width="250">

Download
--------


Usage
-----
To display an RGB color picker dialog:

``` java
ChromaDialog.with(this)
    .initialColor(Color.RED)
    .colorMode(ColorMode.RGB)
    .onColorSelected(color -> /* do your stuff */)
    .create().show();
```

Don't want a dialog? Use `ChromaView` directly:
```
<me.priyesh.chroma.ChromaView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:initialColor="@android:color/red"
    app:colorMode="rgb"/>
    
ChromaView chromaView = ...;
chromaView.getCurrentColor();
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
