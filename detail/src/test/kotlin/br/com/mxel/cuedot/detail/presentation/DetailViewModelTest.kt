package br.com.mxel.cuedot.detail.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.mxel.cuedot.domain.IError
import br.com.mxel.cuedot.domain.State
import br.com.mxel.cuedot.domain.detail.GetMovieDetail
import br.com.mxel.cuedot.domain.entity.Movie
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import io.reactivex.Observable
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailViewModelTest {


    private val dispatcher: CoroutineDispatcher = TestCoroutineDispatcher()

    @RelaxedMockK
    lateinit var getMovieDetail: GetMovieDetail

    @InjectMockKs
    lateinit var viewModel: DetailViewModel

    @RelaxedMockK
    lateinit var movieObserver: Observer<State<Movie>>

    //@get:Rule
    //val coroutinesRule = TestCoroutineRule(dispatcher)

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @After
    fun shutdown() {
        unmockkAll()
    }

    @Test
    fun `Should load more movies`() = runBlockingTest {

        val expectedMovie = Movie(id = 1, title = "Test movie")

        every { getMovieDetail.execute(any()) } returns Observable.just(State.data(expectedMovie))

        viewModel.movie.observeForever(movieObserver)

        viewModel.getMovieDetail(100)

        assertEquals((viewModel.movie.value as State.Data).data, expectedMovie)
    }

    @Test
    fun `Should show error`() = runBlockingTest {

        every { getMovieDetail.execute(any()) } returns Observable.just(State.error())

        viewModel.movie.observeForever(movieObserver)

        viewModel.getMovieDetail(100)

        assertEquals((viewModel.movie.value as State.Error).error, IError.GENERIC_ERROR)
    }
}
