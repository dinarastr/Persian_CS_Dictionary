package ru.dinarastepina.persiancsdictionary.details

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import petros.efthymiou.groovy.utils.BaseUnitTest
import ru.dinarastepina.persiancsdictionary.BaseUnitTest

//todo: remove, move the methods to repository
class WordDetailsServiceShould : BaseUnitTest() {

    lateinit var service: PlaylistDetailsService
    private val id = "100"
    private val api: PlaylistDetailsAPI = mock()
    private val playlistDetails: PlaylistDetails = mock()
    private val exception = RuntimeException("Damn backend developers again 500!!!")

    @Test
    fun fetchPlaylistDetailsFromAPI() = runBlockingTest {
        mockSuccessfulCase()

        service.fetchPlaylistDetails(id).single()

        verify(api, times(1)).fetchPlaylistDetails(id)
    }

    @Test
    fun convertValuesToFlowResultAndEmitThem() = runBlockingTest {
        mockSuccessfulCase()

        assertEquals(Result.success(playlistDetails), service.fetchPlaylistDetails(id).single())
    }

    @Test
    fun emitErrorResultWhenNetworkFails() = runBlockingTest {
        mockErrorCase()

        assertEquals("Something went wrong",
            service.fetchPlaylistDetails(id).single().exceptionOrNull()?.message
        )
    }

    private suspend fun mockErrorCase() {
        whenever(api.fetchPlaylistDetails(id)).thenThrow(exception)

        service = PlaylistDetailsService(api)
    }


    private suspend fun mockSuccessfulCase() {
        whenever(api.fetchPlaylistDetails(id)).thenReturn(playlistDetails)

        service = PlaylistDetailsService(api)
    }


}