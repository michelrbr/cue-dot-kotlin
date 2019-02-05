package br.com.mxel.cuedot.domain.entity

data class Movie (
        val id: Number?,
        val budget: Number?,
        val collection: Collection?,
        val homepage: String?,
        val imdbId: String?,
        val voteCount: Number?,
        val video: Boolean?,
        val voteAverage: Number?,
        val title: String?,
        val popularity: Number?,
        val posterPath: String?,
        val originalLanguage: String?,
        val originalTitle: String?,
        val genres: List<Genre>?,
        val backdropPath: String?,
        val adult: Boolean?,
        val overview: String?,
        val releaseDate: String?,
        val status: String?,
        val tagline: String?,
        val revenue: Number?,
        val runtime: Number?,
        val companies: List<Company>?,
        val countries: List<Country>?,
        val languages: List<Language>?
)