package br.com.mxel.cuedot.domain.search

import br.com.mxel.cuedot.domain.BaseTest
import br.com.mxel.cuedot.domain.Event
import br.com.mxel.cuedot.domain.entity.MovieList
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.reactivex.Single
import org.junit.Test

class SearchTest: BaseTest() {

    @RelaxedMockK
    lateinit var repository: ISearchRepository

    @InjectMockKs
    lateinit var searchMovie: SearchMovie

    @Test
    fun shouldGetMoviesOrderedBy() {

        val expectedMovieList = MovieList(1, 3, 1, null)

        every { repository.searchMovie(any()) } returns
                Single.just(Event.data(expectedMovieList))

        searchMovie.execute("test")
                .test()
                .assertValueAt(0) { it is Event.Loading }
                .assertValueAt(1) { (it as Event.Data).data == expectedMovieList }
    }

    @Test
    fun shouldGetArgumentError() {

        searchMovie.execute("")
                .test()
                .assertValueAt(0) { it is Event.Loading }
                .assertValueAt(1) { (it as Event.Error).error == SearchError.EMPTY_QUERY }
    }
}