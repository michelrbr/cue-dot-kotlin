package br.com.mxel.cuedot.data.entity.remote

import br.com.mxel.cuedot.domain.entity.MovieList
import com.google.gson.annotations.SerializedName

data class MovieListApi(
        val page: Int = 0,
        @SerializedName("total_results")
        val totalResults: Int = 0,
        @SerializedName("total_pages")
        val totalPages: Int = 0,
        @SerializedName("results")
        val movies: List<MovieApi>? = emptyList()
) {
    fun toMovieList() = MovieList(
            page,
            totalResults,
            totalPages,
            movies?.map { it.toMovie() }
    )
}
