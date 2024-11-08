/*
 *    Copyright 2024 Roman Likhachev
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.yugyd.buildlogic.convention.compose

import com.android.build.gradle.LibraryExtension
import com.yugyd.buildlogic.convention.core.ANDROID_LIBRARY_ALIAS
import com.yugyd.buildlogic.convention.core.checkPlugin
import com.yugyd.buildlogic.convention.core.findPluginId
import com.yugyd.buildlogic.convention.core.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class ComposeAndroidLibraryPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            checkPlugin(ANDROID_LIBRARY_ALIAS)
            pluginManager.apply(libs.findPluginId("compose-compiler-plugin"))

            val extension = extensions.getByType<LibraryExtension>()
            extension.configureCompose(target)
        }
    }
}
