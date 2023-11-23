package ru.dinarastepina.persiancsdictionary.dictionary

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.kotlin.times
import org.mockito.kotlin.whenever
import ru.dinarastepina.persiancsdictionary.BaseUnitTest
import ru.dinarastepina.persiancsdictionary.data.repository.DictionaryRepository
import ru.dinarastepina.persiancsdictionary.domain.model.Word

class DictionaryViewModelShould: BaseUnitTest() {

    private val repository: DictionaryRepository = Mockito.mock()
    private val words: List<Word> = Mockito.mock()
    private val expectedSuccess = Result.success(words)
    private val expectedFailure = Result.failure<List<Word>>(RuntimeException("Something went wrong"))

    @Test
    fun dictionaryViewModelShouldGetWordsFromRepository() = runTest {
        val viewmodel = mockSuccessfulCase()

        viewmodel.words.getValueForTest()
        verify(repository, times(1)).getWords()
    }

    @Test
    fun emitsDictionaryFromRepository() = runTest {
        val viewmodel = mockSuccessfulCase()
        Assert.assertEquals(expectedSuccess, viewmodel.words.getValueForTest())
    }

    @Test
    fun emitErrorWhenErrorReceived() = runTest {
        val viewmodel = mockErrorCase()
        Assert.assertEquals(expectedFailure, viewmodel.words.getValueForTest())
    }

    @Test
    fun showSpinnerWhileLoading() = runTest {
        val viewmodel = mockSuccessfulCase()
        viewmodel.loader.captureValues {
            viewmodel.words.getValueForTest()
            Assert.assertEquals(true, values[0])
        }
    }

    @Test
    fun closeSpinnerAfterWordsLoaded() = runTest {
        val viewmodel = mockSuccessfulCase()
        viewmodel.loader.captureValues {
            viewmodel.words.getValueForTest()
            Assert.assertEquals(false, values.last())
        }
    }

    @Test
    fun closeSpinnerAfterLoadingFailed() = runTest {
        val viewmodel = mockErrorCase()
        viewmodel.loader.captureValues {
            viewmodel.words.getValueForTest()
            Assert.assertEquals(false, values.last())
        }
    }

    private fun mockSuccessfulCase(): DictionaryViewModel {
        runBlocking {
            whenever(repository.getWords()).thenReturn(
                flow {
                    emit(expectedSuccess)
                }
            )
        }
        return DictionaryViewModel(repository)
    }

    private fun mockErrorCase(): DictionaryViewModel {
        runBlocking {
            whenever(repository.getWords()).thenReturn(
                flow {
                    emit(expectedFailure)
                }
            )
        }
        return DictionaryViewModel(repository)
    }
}