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
import br.com.mxel.cuedot.R
import br.com.mxel.cuedot.data.remote.ApiProvider
import br.com.mxel.cuedot.presentation.base.BaseActivity
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.squareup.picasso.Picasso


class DetailActivity : BaseActivity() {

    private val movieId: Long by lazy { intent.getLongExtra(MOVIE_ID, 0L) }

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
                    collapsingToolbarLayout.title = "Lock, Stock and Two Smoking Barrels"
                    isShow = true
                } else if (isShow) {
                    collapsingToolbarLayout.title = " "
                    isShow = false
                }
            }
        })

        voteAverage.rating = 3.5F
        movieTitle.text = "Lock, Stock and Two Smoking Barrels"
        overview.text = OVERVIEW
        releaseDate.text = "1998-03-05"
        Picasso.get()
                .load("${ApiProvider.IMAGES_BASE_PATH}/${ApiProvider.BACKDROP_SIZE}/kzeR7BA0htJ7BeI6QEUX3PVp39s.jpg")
                .into(backdrop)
        Picasso.get()
                .load("${ApiProvider.IMAGES_BASE_PATH}/${ApiProvider.BACKDROP_SIZE}/qV7QaSf7f7yC2lc985zfyOJIAIN.jpg")
                .into(cover)
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

    companion object {
        private const val MOVIE_ID = "movieId"

        fun launch(activity: Activity, movieId: Long) {

            val intent = Intent(activity, DetailActivity::class.java)
                    .apply { putExtra(MOVIE_ID, movieId) }
            activity.startActivity(intent)
        }

        private const val OVERVIEW: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce erat lectus, congue et eleifend sed, ornare vel est. Fusce sed iaculis libero. Integer vestibulum hendrerit lorem, sit amet pretium sapien porta eget. Nullam libero lorem, eleifend vitae diam vitae, facilisis bibendum nisl. Ut vel justo nec sem facilisis commodo id a eros. Fusce vitae eros sit amet lectus cursus faucibus. Curabitur eget finibus dui. Morbi ut justo nec quam ultricies accumsan quis quis metus. Nulla viverra libero tortor, imperdiet semper magna vestibulum nec. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Suspendisse potenti. Suspendisse tempus enim nulla, sit amet consectetur lacus dapibus dignissim. Donec vulputate velit elit, a aliquam ligula iaculis in. Suspendisse odio odio, fermentum ac rhoncus eget, tincidunt id neque. Nam ut luctus eros, ac elementum lacus. Maecenas non mauris pellentesque, gravida quam ultricies, tincidunt nibh.\n" +
                "Aliquam convallis sapien id est maximus, eu eleifend velit suscipit. Morbi eu enim justo. Nam fermentum, turpis sed cursus convallis, leo nibh volutpat massa, at efficitur elit erat nec erat. Phasellus id velit sit amet elit venenatis tempus sit amet quis eros. Donec sagittis nunc sem, in commodo magna fermentum ac. Vestibulum in lacus sed nunc elementum scelerisque a in tellus. Fusce sed lacinia urna. Duis diam ante, ullamcorper sed elementum ut, tincidunt sed nulla. Etiam a lectus vestibulum, pretium odio id, vestibulum quam. Sed sodales sapien ut sem dignissim faucibus. Aenean posuere, risus eget mattis imperdiet, quam orci sollicitudin magna, a cursus leo felis quis nisi. Aliquam nec tempor ante, quis feugiat neque. Aliquam eu aliquam magna. Praesent sed ligula magna. Phasellus magna nunc, pulvinar nec augue eu, ornare semper ante."
    }
}