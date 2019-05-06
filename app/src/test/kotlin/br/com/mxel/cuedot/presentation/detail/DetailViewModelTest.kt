package br.com.mxel.cuedot.presentation.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.mxel.cuedot.koin.detailModule
import br.com.mxel.cuedot.domain.BaseTest
import br.com.mxel.cuedot.domain.Event
import br.com.mxel.cuedot.domain.entity.Movie
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.standalone.inject
import org.koin.test.KoinTest
import testAppModule
import testDetailNetworkModule

class DetailViewModelTest : BaseTest(), KoinTest {

    private val viewModel: DetailViewModel by inject()

    @RelaxedMockK
    lateinit var movieObserver: Observer<Event<Movie>>

    @get:Rule
    val rule = InstantTaskExecutorRule()

    override fun initialize() {

        startKoin(listOf(
                testAppModule,
                testDetailNetworkModule,
                detailModule
        ))
    }

    override fun finish() = stopKoin()

    @Test
    fun `Should load more movies`() {

        viewModel.movie.observeForever(movieObserver)

        viewModel.getMovieDetail(100)

        assertEquals((viewModel.movie.value as Event.Data).data.id, 100)
    }
}