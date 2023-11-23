package ru.dinarastepina.persiancsdictionary.data.mapper

import ru.dinarastepina.persiancsdictionary.data.remote.model.WordApiModel
import ru.dinarastepina.persiancsdictionary.domain.model.Word
import javax.inject.Inject

class DataMapper @Inject constructor() {

     fun toDomain(wordsRaw: List<WordApiModel>): List<Word> {
        return wordsRaw.map {
            Word(it.id, it.english, it.meanings)
        }
    }
}
