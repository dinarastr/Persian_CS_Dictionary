package ru.dinarastepina.persiancsdictionary.data.mapper

import ru.dinarastepina.persiancsdictionary.data.local.model.WordDB
import ru.dinarastepina.persiancsdictionary.data.remote.model.WordApiModel
import ru.dinarastepina.persiancsdictionary.domain.model.Word
import javax.inject.Inject

class DataMapper @Inject constructor() {

    fun toDomain(wordRaw: WordApiModel): Word {
        return Word(wordRaw.id, wordRaw.english, wordRaw.meanings)
    }

    fun toDB(wordRaw: WordApiModel): WordDB {
        return WordDB(wordRaw.id, wordRaw.english, wordRaw.meanings)
    }
}
