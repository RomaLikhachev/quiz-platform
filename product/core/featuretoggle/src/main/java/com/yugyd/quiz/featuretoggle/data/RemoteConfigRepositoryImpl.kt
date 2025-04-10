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

package com.yugyd.quiz.featuretoggle.data

import android.content.Context
import com.yugyd.quiz.featuretoggle.data.mapper.TelegramConfigMapper
import com.yugyd.quiz.featuretoggle.data.mapper.UpdateConfigMapper
import com.yugyd.quiz.featuretoggle.data.model.TelegramConfigDto
import com.yugyd.quiz.featuretoggle.data.model.UpdateConfigDto
import com.yugyd.quiz.featuretoggle.domain.RemoteConfigRepository
import com.yugyd.quiz.featuretoggle.domain.model.FeatureToggle
import com.yugyd.quiz.featuretoggle.domain.model.telegram.TelegramConfig
import com.yugyd.quiz.featuretoggle.domain.model.update.UpdateConfig
import com.yugyd.quiz.remoteconfig.api.RemoteConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import timber.log.Timber
import java.util.Locale
import javax.inject.Inject

internal class RemoteConfigRepositoryImpl @Inject internal constructor(
    @ApplicationContext private val context: Context,
    private val remoteConfig: RemoteConfig,
    private val telegramConfigMapper: TelegramConfigMapper,
    private val updateConfigMapper: UpdateConfigMapper,
    private val json: Json,
) : RemoteConfigRepository {

    override suspend fun fetchFeatureToggle(
        featureToggle: FeatureToggle
    ) = remoteConfig.fetchBooleanValue(featureToggle.key)

    override suspend fun fetchForceUpdateVersion() =
        remoteConfig.fetchLongValue(FORCE_UPDATE_KEY).toInt()

    override suspend fun fetchTelegramConfig(): TelegramConfig? {
        return try {
            val dtoList = remoteConfig.fetchStringValue(CONFIG_TELEGRAM_KEY).run {
                json.decodeFromString<List<TelegramConfigDto>>(this)
            }

            val mappedDtoList = dtoList.map(telegramConfigMapper::map)

            val currentLocale = context.resources.configuration.locales.get(0)

            mappedDtoList.firstOrNull {
                it.locale == currentLocale
            } ?: mappedDtoList.firstOrNull {
                it.locale == Locale.ENGLISH
            }
        } catch (expected: Throwable) {
            Timber.e(expected)
            null
        }
    }

    override suspend fun fetchUpdateConfig(): UpdateConfig? {
        return try {
            val dtoList = remoteConfig.fetchStringValue(CONFIG_UPDATE_KEY).run {
                json.decodeFromString<List<UpdateConfigDto>>(this)
            }

            val mappedDtoList = dtoList.map(updateConfigMapper::map)

            val currentLocale = context.resources.configuration.locales.get(0)

            mappedDtoList.firstOrNull {
                it.locale == currentLocale
            } ?: mappedDtoList.firstOrNull {
                it.locale == Locale.ENGLISH
            }
        } catch (expected: Throwable) {
            Timber.e(expected)
            null
        }
    }

    companion object {
        private const val FORCE_UPDATE_KEY = "force_update_version"
        private const val CONFIG_TELEGRAM_KEY = "config_telegram"
        private const val CONFIG_UPDATE_KEY = "config_update"
    }
}
