package br.com.mxel.cuedot.domain

interface IError {

    companion object {
        val GENERIC_ERROR = object : IError {
        }
    }
}