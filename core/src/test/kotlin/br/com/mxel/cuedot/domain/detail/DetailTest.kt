package br.com.mxel.cuedot.domain.detail

import br.com.mxel.cuedot.domain.Event
import br.com.mxel.cuedot.domain.entity.Movie
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test

class DetailTest {

    @RelaxedMockK
    lateinit var repository: IMovieDetailRepository

    @InjectMockKs
    lateinit var getMovieDetail: GetMovieDetail

    @Before
    fun setup() = MockKAnnotations.init(this)

    @After
    fun shutdown() = unmockkAll()

    @Test
    fun shouldGetMovieDetail() {

        val expectedMovie = Movie(id = 1, title = "Test movie")

        every { repository.getMovie(any()) } returns
                Single.just(expectedMovie)

        getMovieDetail.execute(1)
                .test()
                .assertValueAt(0) { it is Event.Loading }
                .assertValueAt(1) { (it as Event.Data).data == expectedMovie }
    }

    @Test
    fun shouldGetPageError() {

        getMovieDetail.execute(0)
                .test()
                .assertValueAt(0) { it is Event.Loading }
                .assertValueAt(1) { (it as Event.Error).error is IllegalArgumentException }
    }
}