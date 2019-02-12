package br.com.mxel.cuedot.domain.entity

data class Movie (
        val id: Long = 0,
        val title: String? = null,
        val posterPath: String? = null,
        val backdropPath: String? = null,
        val overview: String? = null,
        val voteAverage: Float? = null,
        val releaseDate: String? = null,
        val homepage: String? = null,
        val video: Boolean? = false
)