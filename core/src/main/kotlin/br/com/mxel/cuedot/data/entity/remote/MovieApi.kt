package br.com.mxel.cuedot.data.entity.remote

import br.com.mxel.cuedot.data.entity.IDomainMapper
import br.com.mxel.cuedot.data.remote.ApiProvider
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
        val voteAverage: Float = 0.toFloat(),
        @Json(name = "release_date")
        val releaseDate: String? = null,
        val homepage: String? = null,
        val video: Boolean? = false
) : IDomainMapper<Movie> {

    override fun toDomain() = Movie(
            id,
            title,
            String.format("%s/%s%s", ApiProvider.IMAGES_BASE_PATH, ApiProvider.POSTER_SIZE, posterPath),
            String.format("%s/%s%s", ApiProvider.IMAGES_BASE_PATH, ApiProvider.BACKDROP_SIZE, backdropPath),
            overview,
            voteAverage / 2,
            releaseDate,
            homepage,
            video
    )
}