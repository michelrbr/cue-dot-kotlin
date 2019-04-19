package br.com.mxel.cuedot.data.entity.remote

import com.squareup.moshi.Json

data class ErrorApi(
        @Json(name = "status_code")
        val statusCode: Int,

        @Json(name = "status_message")
        val statusMessage: String
)