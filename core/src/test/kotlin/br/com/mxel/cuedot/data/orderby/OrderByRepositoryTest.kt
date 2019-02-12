package br.com.mxel.cuedot.data.orderby

import br.com.mxel.cuedot.data.orderby.remote.IOrderedByClient
import br.com.mxel.cuedot.data.orderby.remote.OrderedByRemoteData
import br.com.mxel.cuedot.data.remote.RemoteClientFactory
import br.com.mxel.cuedot.data.remote.TestInterceptor
import br.com.mxel.cuedot.domain.BaseTest
import br.com.mxel.cuedot.domain.entity.MovieList
import br.com.mxel.cuedot.domain.orderby.Order
import io.mockk.impl.annotations.InjectMockKs
import org.junit.Test

class OrderByRepositoryTest: BaseTest() {

    val interceptor = TestInterceptor()

    val orderedByClient = RemoteClientFactory(
            "https://api.themoviedb.org",
            null, interceptor, false).createClient(IOrderedByClient::class)

    val apiVersion = "3"

    @InjectMockKs
    lateinit var remoteData: OrderedByRemoteData

    @InjectMockKs
    lateinit var repository: OrderedByRepository

    @Test
    fun shouldGetOrderedList() {

        repository.getMoviesOrderedBy(Order.POPULAR, 1)
                .test()
                .assertValue { it.movies!!.isNotEmpty() }
    }

}