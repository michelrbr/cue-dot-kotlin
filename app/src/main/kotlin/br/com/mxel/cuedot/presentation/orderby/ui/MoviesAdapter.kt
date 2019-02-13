package br.com.mxel.cuedot.presentation.orderby.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.mxel.cuedot.R
import br.com.mxel.cuedot.domain.entity.Movie
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class MoviesAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data: List<Movie>? = null
    private val notifier = PublishSubject.create<Movie>()

    var canLoadMore: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == MOVIE_VIEW) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie, parent, false)

            val holder = MovieViewHolder(view)
            /*holder.itemView.setOnClickListener { v ->

                val movie = holder.getMovie()
                if (movie != null) {
                    notifier.onNext(holder.getMovie())
                }
            }*/

            return holder
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_loading, parent, false)
            return LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is MovieViewHolder) {
            val movie = data!![position]
            holder.setMovie(movie)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (canLoadMore && data != null && position > data!!.size - 1) LOADING_VIEW else MOVIE_VIEW
    }

    override fun getItemCount(): Int {

        var count = 0

        if (data != null) {
            count = if (canLoadMore) data!!.size + 1 else data!!.size
        }

        return count
    }

    fun setData(data: List<Movie>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun asObservable(): Observable<Movie> {
        return notifier
    }

    companion object {
        private const val MOVIE_VIEW = 0
        private const val LOADING_VIEW = 1
    }
}