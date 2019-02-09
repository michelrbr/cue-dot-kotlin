package br.com.mxel.cuedot.domain.detail

import br.com.mxel.cuedot.domain.entity.Movie
import io.reactivex.Single

interface IMovieDetailRepository {

    fun getMovie(movieId: Int): Single<Movie>
}