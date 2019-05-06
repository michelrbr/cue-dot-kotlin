package br.com.mxel.cuedot.presentation.orderby

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.mxel.cuedot.koin.orderByModule
import br.com.mxel.cuedot.domain.BaseTest
import br.com.mxel.cuedot.domain.Event
import br.com.mxel.cuedot.domain.orderby.Order
import io.mockk.confirmVerified
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import io.mockk.verifySequence
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.standalone.inject
import org.koin.test.KoinTest
import testAppModule
import testOrderNetworkModule

class OrderedByViewModelTest : BaseTest(), KoinTest {

    private val viewModel: OrderedByViewModel by inject()

    @RelaxedMockK
    lateinit var orderObserver: Observer<Order>

    @RelaxedMockK
    lateinit var refreshObserver: Observer<Boolean>

    @RelaxedMockK
    lateinit var nextPageObserver: Observer<Boolean>

    @RelaxedMockK
    lateinit var errorObserver: Observer<Event.Error?>

    @get:Rule
    val rule = InstantTaskExecutorRule()

    override fun initialize() {

        startKoin(listOf(
                testAppModule,
                testOrderNetworkModule,
                orderByModule
        ))
    }

    override fun finish() = stopKoin()

    @Test
    fun `Should load more movies`() {

        viewModel.currentOrder.observeForever(orderObserver)
        viewModel.refreshLoading.observeForever(refreshObserver)
        viewModel.hasNextPage.observeForever(nextPageObserver)
        viewModel.error.observeForever(errorObserver)

        viewModel.getMovies(Order.POPULAR)

        assertEquals(viewModel.movies.value?.size, 20)

        viewModel.loadMore()

        verify(exactly = 1) { orderObserver.onChanged(Order.POPULAR) }

        verifySequence {
            nextPageObserver.onChanged(false)
            nextPageObserver.onChanged(true)
            nextPageObserver.onChanged(false)
        }

        verifySequence {
            refreshObserver.onChanged(false)
            refreshObserver.onChanged(true)
            refreshObserver.onChanged(false)
        }

        verify(exactly = 1) { errorObserver.onChanged(null) }

        assertEquals(viewModel.movies.value?.size, 40)

        confirmVerified(nextPageObserver)
        confirmVerified(refreshObserver)
    }
}