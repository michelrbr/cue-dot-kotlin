package br.com.mxel.cuedot.presentation.orderby.ui

import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import br.com.mxel.cuedot.R
import br.com.mxel.cuedot.domain.entity.Movie
import butterknife.BindView
import butterknife.ButterKnife
import com.squareup.picasso.Picasso

class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var _currentMovie: Movie? = null
    val currentMovie: Movie?
        get() = _currentMovie

    // UI
    @BindView(R.id.movie_title)
    lateinit var title: AppCompatTextView
    @BindView(R.id.release_date_text_view)
    lateinit var date: AppCompatTextView
    @BindView(R.id.rating)
    lateinit var ratingBar: AppCompatRatingBar
    @BindView(R.id.movie_cover)
    lateinit var movieCover: ImageView

    init {
        ButterKnife.bind(this, view)
    }

    fun setMovie(movie: Movie) {

        _currentMovie = movie
        title.text = movie.title
        date.text = movie.releaseDate
        ratingBar.rating = movie.voteAverage!! / 2
        Picasso.get()
                .load(movie.posterPath)
                .into(movieCover)
    }
}

