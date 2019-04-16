package br.com.mxel.cuedot.presentation.orderby

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.mxel.cuedot.domain.Event
import br.com.mxel.cuedot.domain.SchedulerProvider
import br.com.mxel.cuedot.domain.entity.Movie
import br.com.mxel.cuedot.domain.entity.MovieList
import br.com.mxel.cuedot.domain.orderby.GetMoviesOrderedBy
import br.com.mxel.cuedot.domain.orderby.IOrderedByRepository
import br.com.mxel.cuedot.domain.orderby.Order
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest

class OrderedByViewModelTest : KoinTest {

    private val schedulerProvider = SchedulerProvider(Schedulers.trampoline(), Schedulers.trampoline())

    @RelaxedMockK
    lateinit var repository: IOrderedByRepository

    @InjectMockKs
    lateinit var getMoviesOrderedBy: GetMoviesOrderedBy

    @InjectMockKs
    lateinit var viewModel: OrderedByViewModel

    @RelaxedMockK
    lateinit var moviesObserver: Observer<ArrayList<Movie>>

    @RelaxedMockK
    lateinit var nextPageObserver: Observer<Boolean>

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() = MockKAnnotations.init(this)

    @After
    fun shutdown() = unmockkAll()

    @Test
    fun `Should load more movies`() {

        val firstExpectedMovies = arrayListOf(
                Movie(1, "First"),
                Movie(2, "Second"),
                Movie(3, "Third")
        )

        val secondExpectedMovies = arrayListOf(
                Movie(4, "Forth"),
                Movie(5, "Fifth"),
                Movie(6, "Sixth")
        )

        val finalExpectedMovies = ArrayList(firstExpectedMovies + secondExpectedMovies)

        val firsExpectedResult = MovieList(1, 6, 2, firstExpectedMovies)
        val secondExpectedResult = MovieList(2, 6, 2, secondExpectedMovies)

        every { repository.getMoviesOrderedBy(any(), 1) } returns
                Single.just(Event.data(firsExpectedResult))

        every { repository.getMoviesOrderedBy(any(), 2) } returns
                Single.just(Event.data(secondExpectedResult))

        viewModel.movies.observeForever(moviesObserver)
        viewModel.hasNextPage.observeForever(nextPageObserver)

        viewModel.getMovies(Order.POPULAR)

        verify { nextPageObserver.onChanged(true) }
        verifyOrder {
            moviesObserver.onChanged(arrayListOf())
            moviesObserver.onChanged(firstExpectedMovies)
        }

        viewModel.loadMore()

        verify { nextPageObserver.onChanged(false) }
        verify { moviesObserver.onChanged(finalExpectedMovies) }

        confirmVerified(moviesObserver)
        confirmVerified(nextPageObserver)

        assertEquals(
                arrayListOf(1, 2, 3, 4, 5, 6),
                finalExpectedMovies.map { it.id.toInt() }
        )
    }
}