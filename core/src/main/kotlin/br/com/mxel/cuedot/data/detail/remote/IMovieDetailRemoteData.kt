package br.com.mxel.cuedot.data.detail.remote

import br.com.mxel.cuedot.domain.Event
import br.com.mxel.cuedot.domain.entity.Movie
import io.reactivex.Single

interface IMovieDetailRemoteData {

    fun getMovie(movieId: Long): Single<Event<Movie>>
}