package br.com.mxel.cuedot.presentation.orderby.widget

import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.appcompat.widget.AppCompatTextView
import br.com.mxel.cuedot.R
import br.com.mxel.cuedot.domain.entity.Movie
import br.com.mxel.cuedot.presentation.base.BaseViewHolder
import butterknife.BindView
import butterknife.ButterKnife
import com.squareup.picasso.Picasso

class MovieViewHolder(view: View) : BaseViewHolder<Movie>(view) {

    // UI
    @BindView(R.id.title_text_view)
    lateinit var title: AppCompatTextView
    @BindView(R.id.release_date_text_view)
    lateinit var date: AppCompatTextView
    @BindView(R.id.vote_average_rating)
    lateinit var ratingBar: AppCompatRatingBar
    @BindView(R.id.cover_image)
    lateinit var movieCover: ImageView

    init {
        ButterKnife.bind(this, view)
        itemView.setOnClickListener {
            currentItem?.run { notifyItemClick.onNext(this) }
        }
    }

    override fun bindItem(item: Movie?) {

        currentItem = item

        if (currentItem != null) {

            title.text = currentItem?.title
            date.text = currentItem?.releaseDate
            ratingBar.rating = currentItem?.voteAverage!!
            Picasso.get()
                    .load(currentItem?.posterPath)
                    .into(movieCover)
        }
    }
}

