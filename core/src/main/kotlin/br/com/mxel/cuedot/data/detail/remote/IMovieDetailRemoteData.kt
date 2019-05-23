package br.com.mxel.cuedot.data.detail.remote

import br.com.mxel.cuedot.domain.State
import br.com.mxel.cuedot.domain.entity.Movie
import io.reactivex.Single

interface IMovieDetailRemoteData {

    fun getMovie(movieId: Long): Single<State<Movie>>
}