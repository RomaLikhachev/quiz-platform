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

package com.yugyd.quiz.ui.subcourses

import androidx.lifecycle.SavedStateHandle
import com.yugyd.quiz.commonui.base.BaseViewModel
import com.yugyd.quiz.core.Logger
import com.yugyd.quiz.core.coroutinesutils.DispatchersProvider
import com.yugyd.quiz.core.runCatch
import com.yugyd.quiz.domain.courses.CourseInteractor
import com.yugyd.quiz.domain.courses.model.CourseModel
import com.yugyd.quiz.ui.commoncourses.models.ContinueCourseBannerUiModel
import com.yugyd.quiz.ui.subcourses.SubCourseListView.Action
import com.yugyd.quiz.ui.subcourses.SubCourseListView.State
import com.yugyd.quiz.ui.subcourses.SubCourseListView.State.NavigationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SubCourseListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val courseInteractor: CourseInteractor,
    logger: Logger,
    dispatchersProvider: DispatchersProvider,
) :
    BaseViewModel<State, Action>(
        logger = logger,
        dispatchersProvider = dispatchersProvider,
        initialState = State(
            payload = SubCourseArgs(savedStateHandle).subCoursePayload,
        ),
    ) {

    init {
        screenState = screenState.copy(
            parentCourseTitle = screenState.payload.courseTitle,
        )
        loadData()
    }

    override fun handleAction(action: Action) {
        when (action) {
            is Action.OnCourseClicked -> onCourseClicked(action.item)

            Action.OnSnackbarDismissed -> {
                screenState = screenState.copy(showErrorMessage = false)
            }

            Action.OnNavigationHandled -> {
                screenState = screenState.copy(navigationState = null)
            }

            Action.OnContinueThemeBannerClicked -> {
                onContinueThemeBannerClicked()
            }

            Action.OnHideThemeBannerClicked -> {
                screenState = screenState.copy(continueCourseBanner = null)
            }

            Action.OnSearchClicked -> onSearchClicked()

            Action.OnBackPressed -> {
                screenState = screenState.copy(
                    navigationState = NavigationState.Back,
                )
            }
        }
    }

    private fun onCourseClicked(item: CourseModel) {
        val navigationState = if (item.isDetail) {
            NavigationState.NavigateToCourseDetail(
                id = item.id,
                title = item.name,
            )
        } else {
            NavigationState.NavigateToSubCourse(
                id = item.id,
                title = item.name,
                isHideContinueBanner = screenState.continueCourseBanner == null,
            )
        }
        screenState = screenState.copy(navigationState = navigationState)
    }

    private fun loadData() {
        vmScopeErrorHandled.launch {
            screenState = screenState.copy(
                isLoading = true,
                isWarning = false,
                items = emptyList(),
            )

            runCatch(
                block = {
                    val courses = courseInteractor.getCourses(
                        parentCourseId = screenState.payload.courseId,
                    )
                    val currentCourse = courseInteractor.getCurrentCourse()
                    processData(courses, currentCourse)
                },
                catch = ::processDataError,
            )
        }
    }

    private fun processData(
        courses: List<CourseModel>,
        current: CourseModel?,
    ) {
        val continueCourseBanner = current?.let {
            ContinueCourseBannerUiModel(
                id = it.id,
                title = it.name,
            )
        }
        screenState = screenState.copy(
            isLoading = false,
            isWarning = false,
            items = courses,
            continueCourseBanner = continueCourseBanner,
        )
    }

    private fun processDataError(error: Throwable) {
        screenState = screenState.copy(
            isLoading = false,
            isWarning = true,
            items = emptyList(),
            continueCourseBanner = null,
            showErrorMessage = true,
        )
        processError(error)
    }

    private fun onContinueThemeBannerClicked() {
        val continueCourseBanner = screenState.continueCourseBanner ?: return

        screenState = screenState.copy(
            navigationState = NavigationState.NavigateToCourseDetail(
                id = continueCourseBanner.id,
                title = continueCourseBanner.title,
            ),
        )
    }

    private fun onSearchClicked() {
        screenState = screenState.copy(
            navigationState = NavigationState.NavigateToSearch,
        )
    }
}
