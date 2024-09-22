package com.yugyd.quiz.domain.game.api

import com.yugyd.quiz.domain.game.api.exception.QuestTypeException
import com.yugyd.quiz.domain.game.api.model.Quest
import com.yugyd.quiz.domain.game.api.model.QuestType
import javax.inject.Inject

class UnknownQuestInteractor @Inject constructor() : QuestInteractor {

    override fun isTypeHandled(type: QuestType): Boolean {
        throw QuestTypeException("Unknown quest model")
    }

    override fun isQuestHandled(quest: BaseQuestDomainModel): Boolean {
        throw QuestTypeException("Unknown quest model")
    }

    override fun isTrueAnswer(
        quest: BaseQuestDomainModel,
        index: Int,
        userAnswer: String
    ) = throw QuestTypeException("Unknown quest model")

    override fun getQuestModel(quest: Quest) = throw QuestTypeException("Unknown quest model")

    override fun getHighlightModel(
        quest: BaseQuestDomainModel,
        index: Int,
        isSuccess: Boolean
    ) = throw QuestTypeException("Unknown quest model")
}