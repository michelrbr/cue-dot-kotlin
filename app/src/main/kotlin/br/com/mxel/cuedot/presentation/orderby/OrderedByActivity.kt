package br.com.mxel.cuedot.presentation.orderby

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.mxel.cuedot.R
import br.com.mxel.cuedot.domain.Event
import br.com.mxel.cuedot.domain.entity.Movie
import br.com.mxel.cuedot.domain.orderby.Order
import br.com.mxel.cuedot.extension.message
import br.com.mxel.cuedot.presentation.base.BaseActivity
import br.com.mxel.cuedot.presentation.orderby.ui.MoviesAdapter
import butterknife.BindArray
import butterknife.BindView
import butterknife.ButterKnife
import org.koin.android.viewmodel.ext.android.viewModel

class OrderedByActivity : BaseActivity() {

    private val viewModel: OrderedByViewModel by viewModel()
    private val moviesAdapter = MoviesAdapter()
    private val currentOrder: Order by lazy { intent.getSerializableExtra(ORDER) as Order }
    private val currentTitle:String
        get() = when (currentOrder) {
            Order.POPULAR -> orderArray[0]
            Order.TOP_RATED -> orderArray[1]
            Order.NOW_PLAYING -> orderArray[2]
            Order.UPCOMING -> orderArray[3]
        }

    private var isLoading = false

    @BindArray(R.array.order_by_list)
    lateinit var orderArray: Array<String>

    // UI
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

        setupView()

        viewModel.movies.observe(this, Observer { showMovies(it) })
        viewModel.refreshLoading.observe(this, Observer { showLoadingStatus(it) })
        viewModel.error.observe(this, Observer { showError(it) })
        viewModel.hasNextPage.observe(this, Observer { moviesAdapter.canLoadMore = it })
    }

    private fun setupView() {

        title = currentTitle

        moviesRecyclerView.also {
            it.layoutManager = LinearLayoutManager(this)
            it.setHasFixedSize(true)
            it.adapter = moviesAdapter
            it.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager

                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    if (
                            !isLoading
                            && (viewModel.hasNextPage.value == true)
                            && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    ) {
                        isLoading = true
                        viewModel.loadMore()
                    }
                }
            })
        }
    }

    private fun showError(error: Event.Error?) {

        if (error != null) {
            errorTextView.visibility = View.VISIBLE
            loadingProgress.visibility = View.GONE
            moviesRecyclerView.visibility = View.GONE
            errorTextView.text = getString(error.message(error.error))
        }
    }

    private fun showMovies(movies: List<Movie>) {

        if (movies.isEmpty()) {
            viewModel.getMovies(currentOrder)
        } else {
            isLoading = false
            moviesAdapter.setData(movies)
        }
    }

    private fun showLoadingStatus(loading: Boolean) {

        if (loading) {
            loadingProgress.visibility = View.VISIBLE
            moviesRecyclerView.visibility = View.GONE
            errorTextView.visibility = View.GONE
        } else {
            loadingProgress.visibility = View.GONE
            moviesRecyclerView.visibility = View.VISIBLE
            errorTextView.visibility = View.GONE
        }
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