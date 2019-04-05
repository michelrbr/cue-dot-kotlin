package br.com.mxel.cuedot.presentation.orderby.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import br.com.mxel.cuedot.R
import br.com.mxel.cuedot.domain.entity.Movie
import br.com.mxel.cuedot.presentation.base.BaseListAdapter
import br.com.mxel.cuedot.presentation.base.BaseListViewHolder

class MovieListAdapter : BaseListAdapter<Movie>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseListViewHolder<Movie> {

        return if (viewType == ITEM_VIEW) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_view_holder, parent, false)
            MovieViewHolder(view)
        } else {
            buildLoadingView(parent)
        }
    }

    override fun onBindViewHolder(holder: BaseListViewHolder<Movie>, position: Int) {

        if (!holder.loadingMode) {

            if (itemCount > 0) {
                holder.bindItem(getItem(position))
            }
        }
    }

    private companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return false
                //return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return false
                //return oldItem.id == newItem.id
            }
        }
    }
}