package br.com.mxel.cuedot.data.remote

import br.com.mxel.cuedot.data.entity.IDomainMapper
import br.com.mxel.cuedot.data.entity.remote.ErrorApi
import br.com.mxel.cuedot.domain.IError
import br.com.mxel.cuedot.domain.State
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.reactivex.Single
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

fun <T : IDomainMapper<R>, R> Single<T>.mapToState(): Single<State<R>> = this
        .map { State.data(it.toDomain()) }
        .onErrorReturn {
            when (it) {
                is UnknownHostException -> State.error(RemoteError.CONNECTION_LOST)
                is HttpException -> State.error(parseError(it).toDomain(), it)
                else -> (State.error())
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