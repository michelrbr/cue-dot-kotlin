package br.com.mxel.cuedot.domain

sealed class Event<out T> {

    data class Data<T>(val data: T) : Event<T>()

    data class Error(val error: IError, val cause: Throwable?) : Event<Nothing>()

    object Loading : Event<Nothing>()

    object Idle : Event<Nothing>()

    fun isData() = this is Data

    fun isError() = this is Error

    fun isLoading() = this is Loading

    fun isIdle() = this is Idle

    companion object {

        fun <T> data(data: T): Event<T> = Data(data)

        fun <T> error(
                error: IError = IError.GENERIC_ERROR,
                cause: Throwable? = null
        ): Event<T> = Error(error, cause)

        fun <T> loading(): Event<T> = Loading

        fun <T> idle(): Event<T> = Idle
    }
}