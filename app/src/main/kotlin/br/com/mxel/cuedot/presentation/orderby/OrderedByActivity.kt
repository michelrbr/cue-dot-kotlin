package br.com.mxel.cuedot.presentation.orderby

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.mxel.cuedot.R
import br.com.mxel.cuedot.domain.orderby.Order
import br.com.mxel.cuedot.presentation.base.BaseActivity
import br.com.mxel.cuedot.presentation.base.PagedAdapter
import br.com.mxel.cuedot.presentation.ui.MovieListView
import butterknife.BindArray
import butterknife.BindView
import butterknife.ButterKnife
import org.koin.android.viewmodel.ext.android.viewModel

class OrderedByActivity : BaseActivity() {

    private val viewModel: OrderedByViewModel by viewModel()

    @BindArray(R.array.order_by_list)
    lateinit var orderArray: Array<String>

    // UI
    @BindView(R.id.movie_list)
    lateinit var moviesListView: MovieListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ordered_by_activity)

        unbinder = ButterKnife.bind(this)

        moviesListView.registerLifeCycleOwner(this)

        moviesListView.setLoadMoreListener( object : PagedAdapter.ILoadMoreListener {
            override fun onLoadMore() {
                viewModel.loadMore()
            }
        })

        moviesListView.refreshListener = SwipeRefreshLayout.OnRefreshListener {
            viewModel.getMovies(viewModel.currentOrder.value!!)
        }

        viewModel.currentOrder.observe(this, Observer { setupView(it) })
        viewModel.movies.observe(this, Observer { moviesListView.showMovies(it) })
        viewModel.refreshLoading.observe(this, Observer { moviesListView.showLoadingStatus(it) })
        viewModel.error.observe(this, Observer { moviesListView.showError(it) })
        viewModel.hasNextPage.observe(this, Observer { moviesListView.hasNextPage = it })

        if (viewModel.movies.value?.isEmpty() == true) {

            val currentOrder: Order = if (savedInstanceState?.containsKey(ORDER) == true) {
                savedInstanceState[ORDER] as Order
            } else if (intent.hasExtra(ORDER)) {
                intent.getSerializableExtra(ORDER) as Order
            } else {
                Order.POPULAR
            }

            viewModel.getMovies(currentOrder)
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
            AlertDialog.Builder(this).apply {
                setItems(orderArray) { dialogInterface, position ->

                    viewModel.getMovies(Order.values()[position])
                    moviesListView.reset()
                    dialogInterface.dismiss()
                }
            }.show()
        }
        return super.onOptionsItemSelected(item)
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