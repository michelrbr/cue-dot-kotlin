package br.com.mxel.cuedot.data.detail.remote

import br.com.mxel.cuedot.data.entity.remote.MovieApi
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface IMovieDetailClient {

    // https://developers.themoviedb.org/3/getting-started/append-to-response
    // Append videos and images to response (But it comes on ListVideoResult)
    // &append_to_response=videos,images
    @GET("movie/{movie_id}")
    fun getMovie(@Path("movie_id") movieId: Long): Single<MovieApi>
}