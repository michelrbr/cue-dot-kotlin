package br.com.mxel.cuedot.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class MovieInterceptor(
        private val apiKey: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url()
        val newHttpUrl = originalHttpUrl.newBuilder()
                .setQueryParameter(API_QUERY, apiKey)
                .build()

        val newRequest = originalRequest.newBuilder()
                .url(newHttpUrl)
                .build()

        return chain.proceed(newRequest)
    }

    companion object {
        private const val API_QUERY: String = "api_key"
    }
}