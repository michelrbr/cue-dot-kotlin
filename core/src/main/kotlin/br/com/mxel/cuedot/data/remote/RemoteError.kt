package br.com.mxel.cuedot.data.remote

import br.com.mxel.cuedot.domain.IError

enum class RemoteError : IError {
    CONNECTION_LOST,
    SUSPENDED_API_KEY,
    REQUEST_COUNT_EXCEEDED,
    INVALID_REQUEST_TOKEN,
    RESOURCE_NOT_FOUND;
}