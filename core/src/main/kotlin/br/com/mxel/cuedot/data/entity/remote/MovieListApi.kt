package br.com.mxel.cuedot.data.entity.remote

import br.com.mxel.cuedot.data.entity.IDomainMapper
import br.com.mxel.cuedot.domain.entity.MovieList
import com.squareup.moshi.Json

data class MovieListApi(
        val page: Int = 0,
        @Json(name = "total_results")
        val totalResults: Int = 0,
        @Json(name = "total_pages")
        val totalPages: Int = 0,
        @Json(name = "results")
        val movies: List<MovieApi>? = emptyList()
) : IDomainMapper<MovieList> {

    override fun toDomain() = MovieList(
            page,
            totalResults,
            totalPages,
            movies?.map { it.toDomain() }
    )
}
