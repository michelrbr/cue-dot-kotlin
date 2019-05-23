package br.com.mxel.cuedot.domain

sealed class State<out T> {

    data class Data<T>(val data: T) : State<T>()

    data class Error(val error: IError, val cause: Throwable? = null) : State<Nothing>()

    object Loading : State<Nothing>()

    object Idle : State<Nothing>()

    fun isData() = this is Data

    fun isError() = this is Error

    fun isLoading() = this is Loading

    fun isIdle() = this is Idle

    companion object {

        fun <T> data(data: T): State<T> = Data(data)

        fun <T> error(
                error: IError = IError.GENERIC_ERROR,
                cause: Throwable? = null
        ): State<T> = Error(error, cause)

        fun <T> loading(): State<T> = Loading

        fun <T> idle(): State<T> = Idle
    }
}