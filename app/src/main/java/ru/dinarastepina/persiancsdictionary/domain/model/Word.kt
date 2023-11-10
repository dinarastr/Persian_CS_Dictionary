package ru.dinarastepina.persiancsdictionary.domain.model

data class Word(
    val word: String,
    val translations: List<String>
)