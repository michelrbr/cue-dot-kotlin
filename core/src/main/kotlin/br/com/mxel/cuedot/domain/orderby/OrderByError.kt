package br.com.mxel.cuedot.domain.orderby

import br.com.mxel.cuedot.domain.IError

enum class OrderByError: IError {
    INVALID_PAGE,
    EMPTY_ORDER
}