package br.com.mxel.cuedot.presentation.orderby

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.mxel.cuedot.di.orderByModule
import br.com.mxel.cuedot.domain.BaseTest
import br.com.mxel.cuedot.domain.entity.Movie
import br.com.mxel.cuedot.domain.orderby.Order
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.standalone.inject
import org.koin.test.KoinTest
import testAppModule
import testNetworkModule

class OrderedByViewModelTest : BaseTest(), KoinTest {

    /*private val schedulerProvider = SchedulerProvider(Schedulers.trampoline(), Schedulers.trampoline())

    @RelaxedMockK
    lateinit var repository: IOrderedByRepository

    @InjectMockKs
    lateinit var getMoviesOrderedBy: GetMoviesOrderedBy

    @InjectMockKs
    lateinit var viewModel: OrderedByViewModel*/

    val viewModel: OrderedByViewModel by inject()

    @RelaxedMockK
    lateinit var moviesObserver: Observer<ArrayList<Movie>>

    @RelaxedMockK
    lateinit var nextPageObserver: Observer<Boolean>

    @get:Rule
    val rule = InstantTaskExecutorRule()

    override fun initialize() {

        startKoin(listOf(
                testAppModule,
                testNetworkModule,
                orderByModule
        ))
    }

    override fun finish() {

        stopKoin()
    }

    @Test
    fun `Should load more movies`() {

        /*val firstExpectedMovies = arrayListOf(
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
                Single.just(Event.data(secondExpectedResult))*/

        viewModel.movies.observeForever(moviesObserver)
        viewModel.hasNextPage.observeForever(nextPageObserver)

        viewModel.getMovies(Order.POPULAR)

        assertEquals(viewModel.movies.value?.size, 3)

        /*verify { nextPageObserver.onChanged(true) }
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
        )*/
    }
}