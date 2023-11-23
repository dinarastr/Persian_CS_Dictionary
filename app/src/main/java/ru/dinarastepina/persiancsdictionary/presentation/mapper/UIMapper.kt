package ru.dinarastepina.persiancsdictionary.presentation.mapper

import ru.dinarastepina.persiancsdictionary.domain.model.Word
import ru.dinarastepina.persiancsdictionary.presentation.model.UiWord
import javax.inject.Inject

class UIMapper @Inject constructor() {
    fun toUI(words: List<Word>): List<UiWord> {
        return words.map {
            UiWord(it.id, it.word, it.translations)
        }
    }
}