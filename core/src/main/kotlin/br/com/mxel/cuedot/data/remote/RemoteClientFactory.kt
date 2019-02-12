package br.com.mxel.cuedot.data.remote

import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass


class RemoteClientFactory(
        baseUrl: String,
        cache: Cache?,
        interceptor: Interceptor?,
        debug: Boolean = false,
        connectTimeout: Long = 10,
        writeTimeout: Long = 10,
        readTimeout: Long = 30
) {

    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .cache(cache)
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .writeTimeout(writeTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
            .apply { if (debug) addInterceptor(logger) }
            .apply { if (interceptor != null) addInterceptor(interceptor) }
            .build()

    private val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

    fun <T : Any> createClient(serviceClass: KClass<T>): T = retrofit.create(serviceClass.java)
}