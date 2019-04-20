package br.com.mxel.cuedot.data.entity

interface IDomainMapper<T> {
    fun toDomain(): T
}