package br.com.mxel.cuedot.domain.orderby

import br.com.mxel.cuedot.domain.BaseTest
import br.com.mxel.cuedot.domain.State
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
    fun `Should get movies ordered by`() {

        val expectedMovieList = MovieList(1, 3, 1, null)

        every { repository.getMoviesOrderedBy(any(), any()) } returns
                Single.just(State.data(expectedMovieList))

        getMoviesOrderedBy.execute(Order.POPULAR, 1)
                .test()
                .assertValueAt(0) { it is State.Loading }
                .assertValueAt(1) { (it as State.Data).data == expectedMovieList }
    }

    @Test
    fun `Should get error`() {

        every { repository.getMoviesOrderedBy(any(), any()) } returns
                Single.just(State.error())

        getMoviesOrderedBy.execute(Order.NOW_PLAYING, 1)
                .test()
                .assertValueAt(0) { it is State.Loading }
                .assertValueAt(1) { it is State.Error }
    }

    @Test
    fun `Should get page error`() {

        getMoviesOrderedBy.execute(Order.NOW_PLAYING, 0)
                .test()
                .assertValueAt(0) { it is State.Loading }
                .assertValueAt(1) { (it as State.Error).error == OrderByError.INVALID_PAGE }
    }
}