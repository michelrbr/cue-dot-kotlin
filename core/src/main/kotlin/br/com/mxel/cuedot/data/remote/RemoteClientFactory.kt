package br.com.mxel.cuedot.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass


class RemoteClientFactory(
        cacheDir: File?,
        interceptor: Interceptor?,
        debug: Boolean = false,
        connectTimeout: Long = 10,
        writeTimeout: Long = 10,
        readTimeout: Long = 30
) {

    private val baseUrl = ApiProvider.API_BASE_PATH

    private val cacheSize:Long = 10 * 1024 * 1024 // 10 MB

    private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    private val converterFactory = MoshiConverterFactory.create(moshi)

    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .writeTimeout(writeTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
            .apply { if (cacheDir != null) cache(Cache(cacheDir, cacheSize)) }
            .apply { if (debug) addInterceptor(logger) }
            .apply { if (interceptor != null) addInterceptor(interceptor) }
            .build()

    private val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

    fun <T : Any> createClient(serviceClass: KClass<T>): T = retrofit.create(serviceClass.java)
}