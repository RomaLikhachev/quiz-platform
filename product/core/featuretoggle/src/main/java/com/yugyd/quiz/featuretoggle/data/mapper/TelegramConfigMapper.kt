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

package com.yugyd.quiz.featuretoggle.data.mapper

import com.yugyd.quiz.featuretoggle.data.model.TelegramConfigDto
import com.yugyd.quiz.featuretoggle.domain.model.telegram.GameEnd
import com.yugyd.quiz.featuretoggle.domain.model.telegram.MainPopup
import com.yugyd.quiz.featuretoggle.domain.model.telegram.ProfileCell
import com.yugyd.quiz.featuretoggle.domain.model.telegram.TelegramConfig
import com.yugyd.quiz.featuretoggle.domain.model.telegram.TrainPopup
import javax.inject.Inject

class TelegramConfigMapper @Inject internal constructor(
    private val localeMapper: LocaleMapper,
    private val linkMapper: LinkMapper,
) {

    fun map(telegramConfigDto: TelegramConfigDto): TelegramConfig = telegramConfigDto.run {
        return TelegramConfig(
            locale = localeMapper.mapValueToLocale(locale),
            gameEnd = GameEnd(
                buttonTitle = gameEnd.buttonTitle,
                message = gameEnd.message,
                title = gameEnd.title,
            ),
            links = linkMapper.mapToLinks(links),
            mainPopup = MainPopup(
                buttonTitle = mainPopup.buttonTitle,
                title = mainPopup.title,
                message = mainPopup.message,
            ),
            profileCell = ProfileCell(
                message = profileCell.message,
                title = profileCell.title,
            ),
            trainPopup = TrainPopup(
                message = trainPopup.message,
                negativeButtonTitle = trainPopup.negativeButtonTitle,
                positiveButtonTitle = trainPopup.positiveButtonTitle,
                title = trainPopup.title
            )
        )
    }
}
