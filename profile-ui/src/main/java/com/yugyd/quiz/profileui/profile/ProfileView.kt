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

package com.yugyd.quiz.profileui.profile

import com.yugyd.quiz.profileui.model.ProfileUiModel
import com.yugyd.quiz.profileui.model.SwitchItemProfileUiModel

interface ProfileView {

    data class State(
        val items: List<ProfileUiModel> = emptyList(),
        val error: Throwable? = null,
        val isProFeatureEnabled: Boolean = false,
        val isWarning: Boolean = false,
        val isLoading: Boolean = false,
        val showTelegram: Boolean = false,
        val telegramLink: String = "",
        val navigationState: NavigationState? = null,
    ) {

        sealed interface NavigationState {
            object NavigateToProOnboarding : NavigationState
            object NavigateToTransition : NavigationState
            object NavigateToGooglePlay : NavigationState
            object NavigateToShare : NavigationState
            object NavigateToOtherApps : NavigationState
            object NavigateToExternalReportError : NavigationState
            object NavigateToPrivacyPolicy : NavigationState
        }
    }

    sealed interface Action {
        class OnProfileClicked(val item: ProfileUiModel) : Action
        class OnProfileItemChecked(
            val item: SwitchItemProfileUiModel,
            val isChecked: Boolean
        ) : Action

        object OnTelegramHandled : Action

        object OnNavigationHandled : Action
    }
}
