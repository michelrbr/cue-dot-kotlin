package br.com.mxel.cuedot.presentation.orderby

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import br.com.mxel.cuedot.R
import br.com.mxel.cuedot.domain.orderby.Order
import br.com.mxel.cuedot.presentation.base.BaseActivity
import br.com.mxel.cuedot.presentation.ui.MovieListView
import butterknife.BindArray
import butterknife.BindView
import butterknife.ButterKnife
import org.koin.android.viewmodel.ext.android.viewModel

class OrderedByActivity : BaseActivity() {

    private val viewModel: OrderedByViewModel by viewModel()
    private var currentOrder: Order? = null
        get() {
            if (field == null) {
                field = intent.getSerializableExtra(ORDER) as Order
            }
            return field
        }
    private val currentTitle: String
        get() = when (currentOrder) {
            Order.POPULAR -> orderArray[0]
            Order.TOP_RATED -> orderArray[1]
            Order.NOW_PLAYING -> orderArray[2]
            Order.UPCOMING -> orderArray[3]
            else -> ""
        }

    @BindArray(R.array.order_by_list)
    lateinit var orderArray: Array<String>

    // UI
    @BindView(R.id.movie_list)
    lateinit var moviesListView: MovieListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ordered_by_activity)

        unbinder = ButterKnife.bind(this)

        setupView()

        moviesListView.loadMoreListener = object : MovieListView.ILoadMoreListener {
            override fun onLoadMore() {
                viewModel.loadMore()
            }
        }

        viewModel.movies.observe(this, Observer { moviesListView.showMovies(it) })
        viewModel.refreshLoading.observe(this, Observer { moviesListView.showLoadingStatus(it) })
        viewModel.error.observe(this, Observer { moviesListView.showError(it) })
        viewModel.hasNextPage.observe(this, Observer { moviesListView.hasNextPage = it })

        if (viewModel.movies.value?.isEmpty() == true) {
            viewModel.getMovies(currentOrder!!)
        }
    }

    private fun setupView() {

        title = currentTitle
        moviesListView.registerLifeCycleOwner(this)
    }

    companion object {
        private const val ORDER = "currentOrder"

        fun launch(activity: Activity, order: Order = Order.POPULAR) {

            val intent = Intent(activity, OrderedByActivity::class.java)
                    .apply { putExtra(ORDER, order) }
            activity.startActivity(intent)
        }
    }
}