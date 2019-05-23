package br.com.mxel.cuedot.extension

import br.com.mxel.cuedot.R
import br.com.mxel.cuedot.data.remote.RemoteError
import br.com.mxel.cuedot.domain.IError
import br.com.mxel.cuedot.domain.State
import br.com.mxel.cuedot.domain.orderby.OrderByError

fun State.Error.message(error: IError): Int {
    return when (error) {
        RemoteError.CONNECTION_LOST -> R.string.connection_lost
        OrderByError.INVALID_PAGE -> R.string.invalid_page
        OrderByError.EMPTY_ORDER -> R.string.empty_order
        else -> R.string.generic_error
    }
}