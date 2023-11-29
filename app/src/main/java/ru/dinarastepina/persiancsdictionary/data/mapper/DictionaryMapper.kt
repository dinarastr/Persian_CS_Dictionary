package ru.dinarastepina.persiancsdictionary.data.mapper

import ru.dinarastepina.persiancsdictionary.data.local.model.WordDB
import ru.dinarastepina.persiancsdictionary.data.remote.model.WordApiModel
import ru.dinarastepina.persiancsdictionary.domain.model.Word
import javax.inject.Inject

class DataMapper @Inject constructor() {

    fun toDomain(word: WordApiModel): Word {
        return Word(word.id, word.english, word.meanings)
    }

    fun toDB(word: WordApiModel): WordDB {
        return WordDB(
            id = word.id,
            english = word.english,
            meanings = word.meanings
        )
    }

    fun toDomain(word: WordDB): Word {
        return Word(
            word.id, word.english, word.meanings)
    }
}
