package ru.dinarastepina.persiancsdictionary.dictionary

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import ru.dinarastepina.persiancsdictionary.BaseUnitTest
import ru.dinarastepina.persiancsdictionary.data.remote.api.DictionaryApi
import ru.dinarastepina.persiancsdictionary.data.repository.DictionaryRepository
import ru.dinarastepina.persiancsdictionary.domain.model.Word

class DictionaryRepositoryShould: BaseUnitTest() {


    private val api: DictionaryApi = mock()
    private val words = mock<List<Word>>()
    private val exception = RuntimeException("No!")
    @Test
    fun getDictionaryFromService() = runTest {
        val repository = DictionaryRepository(api)

        repository.getWords()

        verify(api, times(1)).fetchDictionary()
    }

    @Test
    fun emitWordsFromService() = runTest {
        val repository = propagateSuccess()
        assertEquals(words, repository.getWords().first().getOrNull())
    }

    @Test
    fun emitErrorFromService() = runTest {
        val repository = propagateError()
        assertEquals(exception, repository.getWords().first().exceptionOrNull())
    }

    private suspend fun propagateSuccess(): DictionaryRepository {
        whenever(api.fetchDictionary()).thenReturn(
            flow {
                emit(Result.success(words))
            }
        )
        return DictionaryRepository(api)
    }

    private suspend fun propagateError(): DictionaryRepository {
        whenever(api.fetchDictionary()).thenReturn(
            flow {
                emit(Result.failure(exception))
            }
        )
        return DictionaryRepository(api)
    }
}