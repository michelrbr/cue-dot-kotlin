package br.com.mxel.cuedot.domain.orderby

import br.com.mxel.cuedot.domain.BaseTest
import br.com.mxel.cuedot.domain.Event
import br.com.mxel.cuedot.domain.entity.MovieList
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.reactivex.Single
import org.junit.Test

class OrderByTest : BaseTest() {

    @RelaxedMockK
    lateinit var repository: IOrderedByRepository

    @InjectMockKs
    lateinit var getMoviesOrderedBy: GetMoviesOrderedBy

    @Test
    fun shouldGetMoviesOrderedBy() {

        val expectedMovieList = MovieList(1, 3, 1, null)

        every { repository.getMoviesOrderedBy(any(), any()) } returns
                Single.just(Event.data(expectedMovieList))

        getMoviesOrderedBy.execute(Order.POPULAR, 1)
                .test()
                .assertValueAt(0) { it is Event.Loading }
                .assertValueAt(1) { (it as Event.Data).data == expectedMovieList }
    }

    @Test
    fun shouldGetError() {

        every { repository.getMoviesOrderedBy(any(), any()) } returns
                Single.just(Event.error())

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
                .assertValueAt(1) { (it as Event.Error).error == OrderByError.INVALID_PAGE }
    }
}