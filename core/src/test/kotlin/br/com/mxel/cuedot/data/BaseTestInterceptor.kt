package br.com.mxel.cuedot.data

import okhttp3.*
import java.io.File

abstract class BaseTestInterceptor : Interceptor {

    protected fun getJson(path : String) : String {

        val url = this.javaClass.classLoader?.getResource(path)?.path ?: ""

        return File(url).takeIf { it.exists() }?.let { String(it.readBytes()) } ?: ""
    }

    protected fun buildResponse(body: String, request: Request, code: Int = 200): Response {
        return  Response.Builder()
                .code(code)
                .message("OK")
                .request(request)
                .protocol(Protocol.HTTP_1_0)
                .body(ResponseBody.create(MediaType.parse("application/json"), body))
                .addHeader("content-type", "application/json")
                .build()
    }

    protected abstract fun buildJsonPath(url: HttpUrl): String
}