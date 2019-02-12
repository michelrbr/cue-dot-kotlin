package br.com.mxel.cuedot.data.entity.remote

import br.com.mxel.cuedot.domain.entity.Movie
import com.squareup.moshi.Json

data class MovieApi(
        val id: Long = 0,
        val title: String? = null,
        @Json(name = "poster_path")
        val posterPath: String? = null,
        @Json(name = "backdrop_path")
        val backdropPath: String? = null,
        val overview: String? = null,
        @Json(name = "vote_average")
        val voteAverage: Float? = null,
        @Json(name = "release_date")
        val releaseDate: String? = null,
        val homepage: String? = null,
        val video: Boolean? = false
) {

    fun toMovie() = Movie(
            id,
            title,
            posterPath,
            backdropPath,
            overview,
            voteAverage,
            releaseDate,
            homepage,
            video
    )
}