package br.com.mxel.cuedot.presentation.orderby

import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import br.com.mxel.cuedot.R
import br.com.mxel.cuedot.presentation.base.BaseActivity
import butterknife.BindView
import butterknife.ButterKnife

class OrderedByActivity: BaseActivity() {

    @BindView(R.id.movies_recycler_view)
    lateinit var moviesRecyclerView: RecyclerView
    @BindView(R.id.loading_progress)
    lateinit var loadingProgress: ProgressBar
    @BindView(R.id.error_text_view)
    lateinit var errorTextView: AppCompatTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ordered_by_activity)

        unbinder = ButterKnife.bind(this)
    }
}