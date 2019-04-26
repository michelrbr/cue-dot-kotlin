package br.com.mxel.cuedot.data.orderby.remote

import br.com.mxel.cuedot.data.BaseTestInterceptor
import okhttp3.*

class TestOrderByInterceptor : BaseTestInterceptor() {
    override fun intercept(chain: Interceptor.Chain): Response {

        val response: String = getJson(buildJsonPath(chain.request().url()))

        return buildResponse(response, chain.request())
    }

    override fun buildJsonPath(url: HttpUrl): String {

        val page: String = url.queryParameter("page") ?: "1"

        return "popular_page_$page.json"
    }
}