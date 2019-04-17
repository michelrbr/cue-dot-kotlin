package br.com.mxel.cuedot.data.orderby

import br.com.mxel.cuedot.data.orderby.remote.IOrderedByClient
import br.com.mxel.cuedot.data.orderby.remote.OrderedByRemoteData
import br.com.mxel.cuedot.data.orderby.remote.TestOrderByInterceptor
import br.com.mxel.cuedot.data.remote.RemoteClientFactory
import br.com.mxel.cuedot.domain.BaseTest
import br.com.mxel.cuedot.domain.Event
import br.com.mxel.cuedot.domain.orderby.Order
import io.mockk.impl.annotations.InjectMockKs
import org.junit.Test

class OrderByRepositoryTest : BaseTest() {

    private val interceptor = TestOrderByInterceptor()

    private val orderedByClient = RemoteClientFactory(
            null,
            interceptor,
            true
    ).createClient(IOrderedByClient::class)

    private val apiVersion = "3"

    @InjectMockKs
    lateinit var remoteData: OrderedByRemoteData

    @InjectMockKs
    lateinit var repository: OrderedByRepository

    @Test
    fun `Should get ordered list`() {

        repository.getMoviesOrderedBy(Order.POPULAR, 1)
                .test()
                .assertValue { (it as Event.Data).data.movies!!.isNotEmpty() }
    }

}