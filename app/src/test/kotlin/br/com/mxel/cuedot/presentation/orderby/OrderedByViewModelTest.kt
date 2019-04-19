package br.com.mxel.cuedot.presentation.orderby

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.mxel.cuedot.di.orderByModule
import br.com.mxel.cuedot.domain.BaseTest
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
import testNetworkModule

class OrderedByViewModelTest : BaseTest(), KoinTest {

    private val viewModel: OrderedByViewModel by inject()

    @RelaxedMockK
    lateinit var orderObserver: Observer<Order>

    @RelaxedMockK
    lateinit var refreshObserver: Observer<Boolean>

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

        viewModel.currentOrder.observeForever(orderObserver)
        viewModel.refreshLoading.observeForever(refreshObserver)
        viewModel.hasNextPage.observeForever(nextPageObserver)

        viewModel.getMovies(Order.POPULAR)

        assertEquals(viewModel.movies.value?.size, 3)

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

        assertEquals(viewModel.movies.value?.size, 6)

        confirmVerified(nextPageObserver)
        confirmVerified(refreshObserver)

        assertEquals(
                arrayListOf(19404, 278, 238, 287947, 299537, 166428),
                viewModel.movies.value?.map { it.id.toInt() }
        )
    }
}