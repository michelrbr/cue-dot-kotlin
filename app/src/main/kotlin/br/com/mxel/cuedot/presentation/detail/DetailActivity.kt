package br.com.mxel.cuedot.presentation.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import br.com.mxel.cuedot.R
import br.com.mxel.cuedot.domain.State
import br.com.mxel.cuedot.domain.entity.Movie
import br.com.mxel.cuedot.presentation.base.BaseActivity
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.squareup.picasso.Picasso
import org.koin.android.viewmodel.ext.android.viewModel


class DetailActivity : BaseActivity() {

    private val movieId: Long by lazy { intent.getLongExtra(MOVIE_ID, 0L) }

    private val viewModel: DetailViewModel by viewModel()

    // UI
    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar
    @BindView(R.id.app_bar)
    lateinit var appBar: AppBarLayout
    @BindView(R.id.collapsing_toolbar_layout)
    lateinit var collapsingToolbarLayout: CollapsingToolbarLayout
    @BindView(R.id.backdrop_image)
    lateinit var backdrop: AppCompatImageView
    @BindView(R.id.cover_image)
    lateinit var cover: AppCompatImageView
    @BindView(R.id.title_text_view)
    lateinit var movieTitle: AppCompatTextView
    @BindView(R.id.release_date_text_view)
    lateinit var releaseDate: AppCompatTextView
    @BindView(R.id.overview_text_view)
    lateinit var overview: AppCompatTextView
    @BindView(R.id.vote_average_rating)
    lateinit var voteAverage: AppCompatRatingBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)

        unbinder = ButterKnife.bind(this)

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setHomeAsUpIndicator(R.drawable.ic_arrow_white_24dp)
            setDisplayHomeAsUpEnabled(true)
        }

        appBar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.title = title
                    isShow = true
                } else if (isShow) {
                    collapsingToolbarLayout.title = " "
                    isShow = false
                }
            }
        })

        viewModel.movie.observe(this, Observer { onMovieState(it) })
    }

    private fun onMovieState(state: State<Movie>) {

        when (state) {
            is State.Idle -> viewModel.getMovieDetail(movieId)
            is State.Data -> setupView(state.data)
        }
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

    private fun setupView(movie: Movie?) {

        movie?.also {
            title = it.title
            voteAverage.rating = it.voteAverage ?: 0F
            movieTitle.text = it.title
            overview.text = it.overview
            releaseDate.text = it.releaseDate
            Picasso.get()
                    .load(it.backdropPath)
                    .into(backdrop)
            Picasso.get()
                    .load(it.posterPath)
                    .into(cover)
        }
    }

    companion object {
        private const val MOVIE_ID = "movieId"

        fun launch(activity: Activity, movieId: Long) {

            val intent = Intent(activity, DetailActivity::class.java)
                    .apply { putExtra(MOVIE_ID, movieId) }
            activity.startActivity(intent)
        }
    }
}