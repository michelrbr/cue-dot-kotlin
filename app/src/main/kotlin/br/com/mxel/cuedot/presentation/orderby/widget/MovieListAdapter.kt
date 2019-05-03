package br.com.mxel.cuedot.presentation.orderby.widget

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import br.com.mxel.cuedot.R
import br.com.mxel.cuedot.domain.entity.Movie
import br.com.mxel.cuedot.presentation.widget.PagedAdapter
import io.reactivex.subjects.PublishSubject

class MovieListAdapter : PagedAdapter<Movie, MovieViewHolder>(DIFF_CALLBACK) {

    val notifyMovieClick: PublishSubject<Movie> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_view_holder, parent, false)
        return MovieViewHolder(view).apply {
            notifyItemClick.subscribe {
                notifyMovieClick.onNext(it)
            }
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        holder.bindItem(getItem(position))
    }

    private companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}