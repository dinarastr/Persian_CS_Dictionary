package ru.dinarastepina.persiancsdictionary.domain.model

data class Word(
    val id: String,
    val word: String,
    val translations: List<String>
)