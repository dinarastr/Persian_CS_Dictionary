package ru.dinarastepina.persiancsdictionary.presentation.mapper

import ru.dinarastepina.persiancsdictionary.data.remote.model.WordApiModel
import ru.dinarastepina.persiancsdictionary.domain.model.Word
import ru.dinarastepina.persiancsdictionary.presentation.model.UiWord
import javax.inject.Inject

class UIMapper @Inject constructor() {
    fun toUI(word: Word): UiWord {
        return UiWord(word.id, word.word, word.translations)
    }
}