/*
 * Copyright 2024 Roman Likhachev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yugyd.quiz.domain.questai

import com.yugyd.quiz.core.coroutinesutils.DispatchersProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class AiQuestInteractorImpl @Inject constructor(
    private val aiQuestRemoteSource: AiQuestRemoteSource,
    private val dispatcherProvider: DispatchersProvider,
) : AiQuestInteractor {

    override suspend fun getTasks(themeId: Int) = withContext(dispatcherProvider.io) {
        aiQuestRemoteSource.getTasks(themeId = themeId)
    }

    override suspend fun verifyTask(
        quest: String,
        userAnswer: String,
        trueAnswer: String,
    ) = withContext(dispatcherProvider.io) {
        aiQuestRemoteSource.verifyTask(
            quest = quest,
            userAnswer = userAnswer,
            trueAnswer = trueAnswer,
        )
    }

    override suspend fun getThemes(parentThemeId: Int?) = withContext(dispatcherProvider.io) {
        aiQuestRemoteSource.getThemes(parentThemeId = parentThemeId)
    }

    override suspend fun getThemeDetail(themeId: Int) = withContext(dispatcherProvider.io) {
        aiQuestRemoteSource.getThemeDetail(themeId)
    }
}
