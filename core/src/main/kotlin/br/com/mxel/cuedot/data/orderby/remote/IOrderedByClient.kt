package br.com.mxel.cuedot.data.orderby.remote

import br.com.mxel.cuedot.data.entity.remote.MovieListApi
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IOrderedByClient {

    @GET("{version}/movie/{order_by}")
    fun getMoviesOrderedBy(
            @Path("version") version: String,
            @Path("order_by") orderBy: String,
            @Query("page") page: String
    ): Single<MovieListApi>
}