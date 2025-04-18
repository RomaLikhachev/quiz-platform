package com.yugyd.quiz.ui.enterquest

import com.yugyd.quiz.domain.enterquest.EnterQuestModel
import com.yugyd.quiz.domain.game.api.BaseQuestDomainModel
import com.yugyd.quiz.ui.game.api.GameViewModelDelegate
import com.yugyd.quiz.ui.game.api.GameViewModelDelegate.GameViewModelDelegateArgs
import com.yugyd.quiz.ui.game.api.model.BaseQuestUiModel
import com.yugyd.quiz.ui.game.api.model.HighlightUiModel
import com.yugyd.quiz.ui.game.api.model.ProcessAnswerResultModel
import javax.inject.Inject

class EnterGameViewModelDelegate @Inject constructor(
    private val enterQuestUiMapper: EnterQuestUiMapper,
) : GameViewModelDelegate {

    override fun isTypeHandled(domainQuest: BaseQuestDomainModel): Boolean {
        return domainQuest is EnterQuestModel
    }

    override fun getUserAnswers(
        domainQuest: BaseQuestDomainModel,
        quest: BaseQuestUiModel,
    ): Set<String> {
        quest as EnterQuestUiModel

        return setOf(quest.userAnswer)
    }

    override fun processUserAnswer(
        domainQuest: BaseQuestDomainModel,
        quest: BaseQuestUiModel,
        userAnswer: String,
        isSelected: Boolean
    ): ProcessAnswerResultModel {
        quest as EnterQuestUiModel

        return ProcessAnswerResultModel(
            quest = quest.copy(userAnswer = userAnswer),
            isLastAnswer = false,
        )
    }

    override fun getQuestUiModel(domainQuest: BaseQuestDomainModel): BaseQuestUiModel {
        domainQuest as EnterQuestModel

        return enterQuestUiMapper.map(
            model = domainQuest,
            args = EnterQuestUiMapper.EnterArgs(
                userAnswer = "",
                highlightModel = HighlightUiModel.Default,
            )
        )
    }

    override fun updateQuestUiModel(
        domainQuest: BaseQuestDomainModel,
        quest: BaseQuestUiModel,
        highlight: HighlightUiModel,
        args: GameViewModelDelegateArgs?
    ): BaseQuestUiModel {
        domainQuest as EnterQuestModel
        quest as EnterQuestUiModel
        args as EnterGameViewModelDelegateArgs

        return enterQuestUiMapper.map(
            model = domainQuest,
            args = EnterQuestUiMapper.EnterArgs(
                userAnswer = args.manualAnswer,
                highlightModel = highlight,
            )
        )
    }

    override fun getArgs(
        domainQuest: BaseQuestDomainModel,
        userAnswer: String,
        answerButtonIsEnabled: Boolean
    ): GameViewModelDelegateArgs {
        return EnterGameViewModelDelegateArgs(manualAnswer = userAnswer)
    }

    data class EnterGameViewModelDelegateArgs(
        val manualAnswer: String,
    ) : GameViewModelDelegateArgs
}
