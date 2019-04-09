package br.com.mxel.cuedot.presentation.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.mxel.cuedot.R
import br.com.mxel.cuedot.domain.Event
import br.com.mxel.cuedot.domain.entity.Movie
import br.com.mxel.cuedot.extension.message
import br.com.mxel.cuedot.presentation.base.PagedAdapter
import br.com.mxel.cuedot.presentation.orderby.ui.MovieListAdapter
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder

class MovieListView : FrameLayout, LifecycleObserver {

    private var unbinder: Unbinder? = null
    private var isLoading = false
    private val moviesAdapter = MovieListAdapter()

    var refreshListener: SwipeRefreshLayout.OnRefreshListener? = null
        set(value) = refreshView.setOnRefreshListener(value)

    var hasNextPage: Boolean
        get() = moviesAdapter.loadEnable
        set(value) {
            moviesAdapter.loadEnable = value
        }

    // UI
    @BindView(R.id.refresh_layout)
    lateinit var refreshView: SwipeRefreshLayout
    @BindView(R.id.movies_recycler_view)
    lateinit var moviesRecyclerView: RecyclerView
    @BindView(R.id.loading_progress)
    lateinit var loadingProgress: ProgressBar
    @BindView(R.id.error_text_view)
    lateinit var errorTextView: AppCompatTextView

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr)

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.movie_view_list, this)
        unbinder = ButterKnife.bind(this, view)

        setupView()
    }

    fun reset() {
        hasNextPage = true
        moviesAdapter.submitList(null)
    }

    fun registerLifeCycleOwner(owner: LifecycleOwner) {
        owner.lifecycle.addObserver(this)
    }

    fun showError(error: Event.Error?) {

        if (error != null) {
            errorTextView.visibility = View.VISIBLE
            loadingProgress.visibility = View.GONE
            refreshView.visibility = View.GONE
            errorTextView.text = context.getString(error.message(error.error))
        }
    }

    fun showMovies(movies: List<Movie>) {

        isLoading = false
        refreshView.isRefreshing = false
        moviesAdapter.submitList(movies)
    }

    fun showLoadingStatus(loading: Boolean) {

        if (loading) {
            loadingProgress.visibility = View.VISIBLE
            refreshView.visibility = View.GONE
            errorTextView.visibility = View.GONE
        } else {
            loadingProgress.visibility = View.GONE
            refreshView.visibility = View.VISIBLE
            errorTextView.visibility = View.GONE
        }
    }

    fun setLoadMoreListener(listener: PagedAdapter.ILoadMoreListener) {
        moviesAdapter.loadMoreListener = listener
    }

    private fun setupView() {

        moviesRecyclerView.also {
            it.layoutManager = LinearLayoutManager(context)
            it.itemAnimator = null
            it.setHasFixedSize(true)
            it.adapter = moviesAdapter
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun shutdown() {
        unbinder?.unbind()
        moviesAdapter.loadMoreListener = null
    }
}

