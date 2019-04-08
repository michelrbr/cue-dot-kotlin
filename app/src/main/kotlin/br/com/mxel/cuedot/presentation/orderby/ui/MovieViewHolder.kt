package br.com.mxel.cuedot.presentation.orderby.ui

import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.ContentLoadingProgressBar
import br.com.mxel.cuedot.R
import br.com.mxel.cuedot.domain.entity.Movie
import br.com.mxel.cuedot.presentation.base.BaseListViewHolder
import butterknife.BindView
import butterknife.ButterKnife
import com.squareup.picasso.Picasso

class MovieViewHolder(view: View) : BaseListViewHolder<Movie>(view) {

    // UI
    @BindView(R.id.movie_title)
    lateinit var title: AppCompatTextView
    @BindView(R.id.release_date_text_view)
    lateinit var date: AppCompatTextView
    @BindView(R.id.rating)
    lateinit var ratingBar: AppCompatRatingBar
    @BindView(R.id.movie_cover)
    lateinit var movieCover: ImageView
    @BindView(R.id.loading)
    lateinit var loading: ContentLoadingProgressBar

    init {
        ButterKnife.bind(this, view)
    }

    override fun bindItem(item: Movie?) {

        if (item != null) {

            loading.hide()
            currentItem = item
            title.text = currentItem?.title
            date.text = currentItem?.releaseDate
            ratingBar.rating = currentItem?.voteAverage!! / 2
            Picasso.get()
                    .load(currentItem?.posterPath)
                    .into(movieCover)
        } else {
            loading.show()
        }
    }
}

