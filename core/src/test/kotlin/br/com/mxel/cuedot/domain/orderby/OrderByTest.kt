package br.com.mxel.cuedot.domain.orderby

import br.com.mxel.cuedot.domain.Event
import br.com.mxel.cuedot.domain.entity.MovieList
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test

class OrderByTest {

    @RelaxedMockK
    lateinit var repository: IOrderedByRepository

    @InjectMockKs
    lateinit var getMoviesOrderedBy: GetMoviesOrderedBy

    @Before
    fun setup() = MockKAnnotations.init(this)

    @After
    fun shutdown() = unmockkAll()

    @Test
    fun shouldGetMoviesOrderedBy() {

        val expectedMovieList = MovieList(1, 3, 1, null)

        every { repository.getMoviesOrderedBy(any(), any()) } returns
                Single.just(expectedMovieList)

        getMoviesOrderedBy.execute(Order.POPULAR, 1)
                .test()
                .assertValueAt(0) { it is Event.Loading }
                .assertValueAt(1) { it is Event.Data && it.data == expectedMovieList }
    }

    @Test
    fun shouldGetError() {

        every { repository.getMoviesOrderedBy(any(), any()) } returns
                Single.error(Throwable("Testing"))

        getMoviesOrderedBy.execute(Order.NOW_PLAYING, 1)
                .test()
                .assertValueAt(0) { it is Event.Loading }
                .assertValueAt(1) { it is Event.Error }
    }

    @Test
    fun shouldGetPageError() {

        getMoviesOrderedBy.execute(Order.NOW_PLAYING, 0)
                .test()
                .assertValueAt(0) { it is Event.Loading }
                .assertValueAt(1) { it is Event.Error && it.error is IllegalArgumentException }
    }
}