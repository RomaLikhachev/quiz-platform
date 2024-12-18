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

package com.yugyd.quiz.ui.main

import com.yugyd.quiz.domain.api.payload.OnboardingPayload

internal interface MainView {

    data class State(
        val isCorrectFeatureEnabled: Boolean = false,
        val isCoursesFeatureEnabled: Boolean = false,
        val topDestinations: List<TopDestination> = TopDestination.entries,
        val requestPushPermission: Boolean = false,
        val showOnboarding: Boolean = false,
        val onboardingPayload: OnboardingPayload? = null,
        val showTelegram: Boolean = false,
        val telegramLink: String = "",
        val navigationState: NavigationState? = null,
    ) {

        sealed interface NavigationState {
            object Back : NavigationState
            object NavigateToUpdate : NavigationState
            object NavigateToContent : NavigationState
        }
    }

    sealed interface Action {
        object OnOnboardingClicked : Action
        object OnBackPressed : Action
        data class ProcessPushData(val extras: Map<String, String>) : Action
        object RequestPushPermissionHandled : Action
        object OnboardingBottomSheetDismissed : Action
        object OnTelegramHandled : Action
        object OnNavigationHandled : Action
    }
}
