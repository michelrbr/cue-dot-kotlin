package br.com.mxel.cuedot.data.orderby.remote

import br.com.mxel.cuedot.data.BaseTestInterceptor
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

class TestOrderByInterceptor : BaseTestInterceptor() {
    override fun intercept(chain: Interceptor.Chain): Response {

        val (code, response) = getResponse(buildJsonPath(chain.request().url()))

        return buildResponse(response, chain.request(), code)
    }

    override fun buildJsonPath(url: HttpUrl): String {

        val page: String = url.queryParameter("page") ?: "1"

        return getFilePath("popular_page_$page.json")
    }
}