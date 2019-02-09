package br.com.mxel.cuedot.domain.entity

data class Movie (
        val id: Number = 0,
        val budget: Number? = null,
        val collection: Collection? = null,
        val homepage: String? = null,
        val imdbId: String? = null,
        val voteCount: Number? = null,
        val video: Boolean? = false,
        val voteAverage: Number? = null,
        val title: String? = null,
        val popularity: Number? = null,
        val posterPath: String? = null,
        val originalLanguage: String? = null,
        val originalTitle: String? = null,
        val genres: List<Genre> = emptyList(),
        val backdropPath: String? = null,
        val adult: Boolean? = false,
        val overview: String? = null,
        val releaseDate: String? = null,
        val status: String? = null,
        val tagline: String? = null,
        val revenue: Number? = null,
        val runtime: Number? = null,
        val companies: List<Company> = emptyList(),
        val countries: List<Country> = emptyList(),
        val languages: List<Language> = emptyList()
)