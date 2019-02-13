package br.com.mxel.cuedot.presentation.orderby.ui

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.mxel.cuedot.R
import br.com.mxel.cuedot.domain.entity.Movie
import com.squareup.picasso.Picasso

class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var currentMovie: Movie? = null

    fun setMovie(movie: Movie) {

        currentMovie = movie
        (itemView.findViewById(R.id.movie_title) as TextView).text = movie.title
        (itemView.findViewById(R.id.release_date_text_view) as TextView).text = movie.releaseDate
        (itemView.findViewById(R.id.rating) as RatingBar).rating = movie.voteAverage!! / 2
        Picasso.get()
                .load(movie.posterPath)
                .into(itemView.findViewById(R.id.movie_cover) as ImageView)
    }
}

