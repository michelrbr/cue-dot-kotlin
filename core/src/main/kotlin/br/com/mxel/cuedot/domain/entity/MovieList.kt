package br.com.mxel.cuedot.domain.entity

data class MovieList(
        val page: Int,
        val totalResults: Int,
        val totalPages: Int,
        val movies: List<Movie>?
)