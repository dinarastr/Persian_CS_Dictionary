package ru.dinarastepina.persiancsdictionary.presentation.model

data class UiWord(
    val id: String,
    val word: String,
    val translations: List<String>
)