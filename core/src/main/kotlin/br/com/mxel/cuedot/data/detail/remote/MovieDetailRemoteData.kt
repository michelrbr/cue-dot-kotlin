package br.com.mxel.cuedot.data.detail.remote

import br.com.mxel.cuedot.data.remote.ApiProvider
import br.com.mxel.cuedot.data.remote.mapToState
import br.com.mxel.cuedot.domain.State
import br.com.mxel.cuedot.domain.entity.Movie
import io.reactivex.Single

class MovieDetailRemoteData(
        private val client: IMovieDetailClient
): IMovieDetailRemoteData {

    private val apiVersion = ApiProvider.API_VERSION

    override fun getMovie(movieId: Long): Single<State<Movie>> {

        return client.getMovie(apiVersion, movieId).mapToState()
    }
}