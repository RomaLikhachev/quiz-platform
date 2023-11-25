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

package com.yugyd.quiz.domain.content.di

import com.yugyd.quiz.domain.content.ContentClient
import com.yugyd.quiz.domain.content.ContentInteractor
import com.yugyd.quiz.domain.content.ContentInteractorImpl
import com.yugyd.quiz.domain.content.data.ContentClientImpl
import com.yugyd.quiz.domain.content.data.helper.ContentValidatorHelper
import com.yugyd.quiz.domain.content.data.helper.ContentValidatorHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ContentBlModule {

    @Binds
    internal abstract fun bindContentValidatorHelper(
        impl: ContentValidatorHelperImpl,
    ): ContentValidatorHelper

    @Binds
    internal abstract fun bindContentClient(impl: ContentClientImpl): ContentClient

    @Binds
    internal abstract fun bindContentInteractor(impl: ContentInteractorImpl): ContentInteractor
}