package br.com.mxel.cuedot.data.detail.remote

import br.com.mxel.cuedot.data.remote.mapToEvent
import br.com.mxel.cuedot.domain.Event
import br.com.mxel.cuedot.domain.entity.Movie
import io.reactivex.Single

class MovieDetailRemoteData(
        private val client: IMovieDetailClient
): IMovieDetailRemoteData {

    override fun getMovie(movieId: Long): Single<Event<Movie>> {

        return client.getMovie(movieId).mapToEvent()
    }
}