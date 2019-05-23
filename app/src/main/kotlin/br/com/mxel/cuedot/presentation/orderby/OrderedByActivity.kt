package br.com.mxel.cuedot.presentation.orderby

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import br.com.mxel.cuedot.R
import br.com.mxel.cuedot.data.remote.RemoteError
import br.com.mxel.cuedot.domain.State
import br.com.mxel.cuedot.domain.entity.Movie
import br.com.mxel.cuedot.domain.orderby.Order
import br.com.mxel.cuedot.domain.orderby.OrderByError
import br.com.mxel.cuedot.extension.message
import br.com.mxel.cuedot.presentation.base.BaseActivity
import br.com.mxel.cuedot.presentation.detail.DetailActivity
import br.com.mxel.cuedot.presentation.orderby.widget.MovieListAdapter
import br.com.mxel.cuedot.presentation.widget.ListLayout
import br.com.mxel.cuedot.presentation.widget.PagedAdapter
import butterknife.BindArray
import butterknife.BindView
import butterknife.ButterKnife
import io.reactivex.rxkotlin.addTo
import org.koin.android.viewmodel.ext.android.viewModel

class OrderedByActivity : BaseActivity() {

    private val viewModel: OrderedByViewModel by viewModel()

    private val adapter = MovieListAdapter()

    @BindArray(R.array.order_by_list)
    lateinit var orderArray: Array<String>

    // UI
    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar
    @BindView(R.id.movie_list)
    lateinit var moviesListView: ListLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ordered_by_activity)

        unbinder = ButterKnife.bind(this)

        setSupportActionBar(toolbar)

        adapter.loadMoreListener = object : PagedAdapter.ILoadMoreListener {
            override fun onLoadMore() {
                viewModel.loadMore()
            }
        }

        adapter.notifyMovieClick.subscribe {
            DetailActivity.launch(this, it.id)
        }.addTo(disposable)

        moviesListView.adapter = adapter

        moviesListView.setOnRefreshListener {
            viewModel.refresh()
        }

        viewModel.currentOrder.observe(this, Observer { setupView(it) })
        viewModel.movies.observe(this, Observer { onMoviesState(it) })
        viewModel.error.observe(this, Observer { showError(it) })
        viewModel.hasNextPage.observe(this, Observer { adapter.loadEnable = it })

        if (viewModel.movies.value is State.Idle) {

            val currentOrder: Order = when {
                savedInstanceState?.containsKey(ORDER) == true -> savedInstanceState[ORDER] as Order
                intent.hasExtra(ORDER) -> intent.getSerializableExtra(ORDER) as Order
                else -> Order.POPULAR
            }

            viewModel.getMovies(currentOrder)
        }
    }

    private fun onMoviesState(state: State<List<Movie>>) {
        when (state) {
            is State.Data -> {
                moviesListView.isRefreshing = false
                adapter.submitList(state.data)
            }
            is State.Loading -> moviesListView.takeUnless { it.isRefreshing }?.isRefreshing = true
            is State.Error -> moviesListView.showFeedbackStatus(getString(state.message(state.error)))
        }
    }

    private fun showError(error: State.Error?) {

        error?.let {
            when (it.error) {
                OrderByError.EMPTY_ORDER -> showOrderChooserDialog()
                RemoteError.CONNECTION_LOST -> adapter.showLoadMoreError()
                else -> moviesListView.showFeedbackStatus(getString(error.message(it.error)))
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.order_by_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putSerializable(ORDER, viewModel.currentOrder.value)
        super.onSaveInstanceState(outState)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == R.id.action_order_by) {
            showOrderChooserDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showOrderChooserDialog() {
        AlertDialog.Builder(this).apply {
            setItems(orderArray) { dialogInterface, position ->

                adapter.submitList(null)
                viewModel.getMovies(Order.values()[position])
                dialogInterface.dismiss()
            }
        }.show()
    }

    private fun setupView(order: Order?) {

        title = when (order) {
            Order.POPULAR -> orderArray[0]
            Order.TOP_RATED -> orderArray[1]
            Order.NOW_PLAYING -> orderArray[2]
            Order.UPCOMING -> orderArray[3]
            else -> ""
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