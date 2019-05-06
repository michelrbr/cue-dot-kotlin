package br.com.mxel.cuedot.data.detail.remote

import br.com.mxel.cuedot.data.BaseTestInterceptor
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

class TestMovieDetailInterceptor : BaseTestInterceptor() {

    override fun intercept(chain: Interceptor.Chain): Response {

        val (code, response) = getResponse(buildJsonPath(chain.request().url()))

        return buildResponse(response, chain.request(), code)
    }

    override fun buildJsonPath(url: HttpUrl): String {

        val movieId = try {
            url.pathSegments()[2].toInt()
        } catch (e: Exception) {
            0
        }

        return getFilePath("movie_$movieId.json")
    }
}