package br.com.mxel.cuedot.data.entity.remote

import br.com.mxel.cuedot.domain.entity.Movie
import com.google.gson.annotations.SerializedName

data class MovieApi(
        val id: Long = 0,
        val title: String? = null,
        @SerializedName("poster_path")
        val posterPath: String? = null,
        @SerializedName("backdrop_path")
        val backdropPath: String? = null,
        val overview: String? = null,
        @SerializedName("vote_average")
        val voteAverage: Float? = null,
        @SerializedName("release_date")
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