package br.com.mxel.cuedot.detail.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.com.mxel.cuedot.detail.R
import br.com.mxel.cuedot.detail.databinding.ActivityMovieDetailBinding
import br.com.mxel.cuedot.domain.State
import br.com.mxel.cuedot.domain.entity.Movie
import com.google.android.material.appbar.AppBarLayout
import com.squareup.picasso.Picasso
import org.koin.android.viewmodel.ext.android.viewModel

class MovieDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityMovieDetailBinding

    private val movieId: Long by lazy { intent.getLongExtra(MOVIE_ID, 0L) }

    private val viewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        supportActionBar?.apply {
            setHomeAsUpIndicator(R.drawable.ic_arrow_white_24dp)
            setDisplayHomeAsUpEnabled(true)
        }

        binding.appBar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    binding.collapsingToolbarLayout.title = title
                    isShow = true
                } else if (isShow) {
                    binding.collapsingToolbarLayout.title = " "
                    isShow = false
                }
            }
        })

        viewModel.movie.observe(this, Observer { onMovieState(it) })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.movie_detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onMovieState(state: State<Movie>) {

        when (state) {
            is State.Idle -> viewModel.getMovieDetail(movieId)
            is State.Data -> setupView(state.data)
        }
    }

    private fun setupView(movie: Movie?) {

        movie?.also {
            binding.titleTextView.text = it.title
            binding.voteAverageRating.rating = it.voteAverage ?: 0F
            binding.overviewTextView.text = it.title
            binding.overviewTextView.text = it.overview
            binding.releaseDateTextView.text = it.releaseDate
            Picasso.get()
                .load(it.backdropPath)
                .into(binding.backdropImage)
            Picasso.get()
                .load(it.posterPath)
                .into(binding.coverImage)
        }
    }

    companion object {
        private const val MOVIE_ID = "movieId"

        fun launch(activity: Activity, movieId: Long) {

            val intent = Intent(activity, MovieDetailActivity::class.java)
                .apply { putExtra(MOVIE_ID, movieId) }
            activity.startActivity(intent)
        }
    }
}
