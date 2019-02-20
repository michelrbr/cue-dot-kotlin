package br.com.mxel.cuedot.domain.detail

import br.com.mxel.cuedot.domain.BaseTest
import br.com.mxel.cuedot.domain.Event
import br.com.mxel.cuedot.domain.entity.Movie
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.reactivex.Single
import org.junit.Test

class DetailTest : BaseTest() {

    @RelaxedMockK
    lateinit var repository: IMovieDetailRepository

    @InjectMockKs
    lateinit var getMovieDetail: GetMovieDetail

    @Test
    fun shouldGetMovieDetail() {

        val expectedMovie = Movie(id = 1, title = "Test movie")

        every { repository.getMovie(any()) } returns
                Single.just(Event.data(expectedMovie))

        getMovieDetail.execute(1)
                .test()
                .assertValueAt(0) { it is Event.Loading }
                .assertValueAt(1) { (it as Event.Data).data == expectedMovie }
    }

    @Test
    fun shouldGetArgumentError() {

        getMovieDetail.execute(0)
                .test()
                .assertValueAt(0) { it is Event.Loading }
                .assertValueAt(1) { (it as Event.Error).error == DetailError.INVALID_ID }
    }
}