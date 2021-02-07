package br.com.mxel.cuedot.domain.detail

import br.com.mxel.cuedot.domain.State
import br.com.mxel.cuedot.domain.entity.Movie
import io.reactivex.Single

interface IMovieDetailRepository {

    fun getMovie(movieId: Long): Single<State<Movie>>
}
