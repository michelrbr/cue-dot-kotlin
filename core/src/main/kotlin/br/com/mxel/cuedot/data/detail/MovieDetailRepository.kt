package br.com.mxel.cuedot.data.detail

import br.com.mxel.cuedot.data.detail.remote.IMovieDetailRemoteData
import br.com.mxel.cuedot.domain.State
import br.com.mxel.cuedot.domain.detail.IMovieDetailRepository
import br.com.mxel.cuedot.domain.entity.Movie
import io.reactivex.Single

class MovieDetailRepository(
        private val remote: IMovieDetailRemoteData
) : IMovieDetailRepository {

    override fun getMovie(movieId: Long): Single<State<Movie>> {

        return remote.getMovie(movieId)
    }
}