/*
 * Copyright 2016 Priyesh Patel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.priyesh.chroma.internal.models

interface ColorModel {

  class Channel(val nameResourceId: Int,
                val min: Int, val max: Int,
                val extractor: (color: Int) -> Int,
                var progress: Int = 0)

  val channels: List<Channel>

  fun evaluateColor(channels: List<Channel>): Int
}