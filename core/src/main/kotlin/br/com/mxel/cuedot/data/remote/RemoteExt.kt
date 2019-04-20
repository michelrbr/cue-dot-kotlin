package br.com.mxel.cuedot.data.remote

import br.com.mxel.cuedot.data.entity.IDomainMapper
import br.com.mxel.cuedot.data.entity.remote.ErrorApi
import br.com.mxel.cuedot.domain.Event
import br.com.mxel.cuedot.domain.IError
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.reactivex.Single
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

fun <T : IDomainMapper<R>, R> Single<T>.mapToEvent(): Single<Event<R>> = this
        .map { Event.data(it.toDomain()) }
        .onErrorReturn {
            when (it) {
                is UnknownHostException -> Event.error(RemoteError.CONNECTION_LOST)
                is HttpException -> Event.error(parseError(it).toDomain(), it)
                else -> (Event.error())
            }
        }

private val moshi by lazy { Moshi.Builder().add(KotlinJsonAdapterFactory()).build() }

private fun parseError(error: HttpException): IDomainMapper<IError> {

    val json = error.response().errorBody()?.string() ?: ""

    return try {
        moshi.adapter<ErrorApi>(ErrorApi::class.java).fromJson(json) ?: ErrorApi()
    } catch (error: IOException) {
        ErrorApi()
    }
}