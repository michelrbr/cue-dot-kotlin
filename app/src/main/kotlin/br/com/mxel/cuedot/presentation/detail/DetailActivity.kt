package br.com.mxel.cuedot.presentation.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import br.com.mxel.cuedot.presentation.base.BaseActivity

class DetailActivity: BaseActivity() {

    private val movieId: Long by lazy { intent.getLongExtra(MOVIE_ID, 0L) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(TextView(this).apply { text = movieId.toString() })
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