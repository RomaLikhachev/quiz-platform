/*
 *    Copyright 2023 Roman Likhachev
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

package com.yugyd.quiz.themeui.theme

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.yugyd.quiz.domain.model.payload.GamePayload
import com.yugyd.quiz.domain.model.payload.SectionPayload
import com.yugyd.quiz.navigation.topLevelNavOptions

const val THEME_ROUTE = "theme"

fun NavController.navigateToTheme() {
    navigate(THEME_ROUTE, topLevelNavOptions())
}

fun NavGraphBuilder.themeScreen(
    snackbarHostState: SnackbarHostState,
    onNavigateToGame: (GamePayload) -> Unit,
    onNavigateToSection: (SectionPayload) -> Unit,
    onNavigateToTelegram: (String) -> Unit,
) {
    composable(route = THEME_ROUTE) {
        ThemeRoute(
            snackbarHostState = snackbarHostState,
            onNavigateToGame = onNavigateToGame,
            onNavigateToSection = onNavigateToSection,
            onNavigateToTelegram = onNavigateToTelegram,
        )
    }
}
