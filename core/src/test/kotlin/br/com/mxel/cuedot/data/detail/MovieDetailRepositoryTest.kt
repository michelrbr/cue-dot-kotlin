package br.com.mxel.cuedot.data.detail

import br.com.mxel.cuedot.data.detail.remote.IMovieDetailClient
import br.com.mxel.cuedot.data.detail.remote.MovieDetailRemoteData
import br.com.mxel.cuedot.data.detail.remote.TestMovieDetailInterceptor
import br.com.mxel.cuedot.data.remote.RemoteClientFactory
import br.com.mxel.cuedot.data.remote.RemoteError
import br.com.mxel.cuedot.domain.BaseTest
import br.com.mxel.cuedot.domain.Event
import io.mockk.impl.annotations.InjectMockKs
import org.junit.Test

class MovieDetailRepositoryTest : BaseTest() {

    private val orderedByClient = RemoteClientFactory(
            null,
            TestMovieDetailInterceptor(),
            true
    ).createClient(IMovieDetailClient::class)

    @InjectMockKs
    lateinit var remoteData: MovieDetailRemoteData

    @InjectMockKs
    lateinit var repository: MovieDetailRepository

    @Test
    fun `Should get movie detail`() {

        repository.getMovie(100)
                .test()
                .assertValue { (it as Event.Data).data.id == 100L }
    }

    @Test
    fun `Should get movie not found error`() {
        repository.getMovie(10)
                .test()
                .assertValue { (it as Event.Error).error == RemoteError.RESOURCE_NOT_FOUND }
    }
}