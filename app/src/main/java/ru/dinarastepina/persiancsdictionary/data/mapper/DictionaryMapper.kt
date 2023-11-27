package ru.dinarastepina.persiancsdictionary.data.mapper

import ru.dinarastepina.persiancsdictionary.data.remote.model.WordApiModel
import ru.dinarastepina.persiancsdictionary.domain.model.Word
import javax.inject.Inject

class DataMapper @Inject constructor() {

    fun toDomain(wordRaw: WordApiModel): Word {
        return Word(wordRaw.id, wordRaw.english, wordRaw.meanings)
    }
}
