package br.com.mxel.cuedot.presentation.widget

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.*
import br.com.mxel.cuedot.R
import br.com.mxel.cuedot.presentation.base.BaseViewHolder
import io.reactivex.disposables.CompositeDisposable

abstract class PagedAdapter<T, VH : BaseViewHolder<T>> : RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private val helper: AsyncListDiffer<T>

    protected val disposable = CompositeDisposable()

    protected var loading: Boolean = false

    protected var errorState: Boolean = false

    var loadEnable: Boolean = true

    var loadMoreListener: ILoadMoreListener? = null

    private var scrollListener =
            object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                    recyclerView.layoutManager?.also {
                        val firstVisibleItemPosition = (it as LinearLayoutManager).findFirstVisibleItemPosition()

                        if (
                                !loading &&
                                loadEnable &&
                                ((it.childCount + firstVisibleItemPosition) >= (it.itemCount - 5))
                        ) {
                            loading = true
                            loadMoreListener?.onLoadMore()
                        }
                    }
                }
            }

    private val spanSizeManager = object : GridLayoutManager.SpanSizeLookup() {

        var spanCount:Int = 0

        override fun getSpanSize(position: Int): Int {
            return if(getItemViewType(position) == ITEM_VIEW) {
                1
            } else {
                spanCount
            }
        }
    }

    constructor(diffCallback: DiffUtil.ItemCallback<T>) {

        helper = AsyncListDiffer(AdapterListUpdateCallback(this),
                AsyncDifferConfig.Builder(diffCallback).build())
    }

    constructor(config: AsyncDifferConfig<T>) {

        helper = AsyncListDiffer(AdapterListUpdateCallback(this), config)
    }


    abstract fun createItemView(parent: ViewGroup): VH

    abstract fun bindItemView(holder: VH, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            LOADING_VIEW -> createLoadingView(parent)
            ERROR_VIEW -> createErrorView(parent)
            else -> createItemView(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is BaseViewHolder<*>) {
            bindItemView(holder as VH, position)
        }
    }

    override fun getItemViewType(position: Int): Int {

        return position.takeIf { it > helper.currentList.size - 1 }
                ?.let { if (errorState) ERROR_VIEW else LOADING_VIEW }
                ?: ITEM_VIEW
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {

        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addOnScrollListener(scrollListener)
        (recyclerView.layoutManager as? GridLayoutManager)
                ?.also {
                    this.spanSizeManager.spanCount = it.spanCount
                    it.spanSizeLookup = this.spanSizeManager
                }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {

        disposable.clear()
        recyclerView.removeOnScrollListener(scrollListener)
        super.onDetachedFromRecyclerView(recyclerView)
    }

    fun submitList(list: List<T>?) {

        if (list.isNullOrEmpty()) {
            helper.submitList(list)
            return
        }

        loading = false
        helper.submitList(list)

        notifyDataSetChanged()
    }

    fun showLoadMoreError() {

        notifyDataSetChanged()
        errorState = true
    }

    protected fun getItem(position: Int): T {

        return helper.currentList[position]
    }

    override fun getItemCount(): Int {

        return helper.currentList.size.let {
            if (loadEnable && it > 0) it + 1 else it
        }
    }

    protected open fun createErrorView(parent: ViewGroup): RecyclerView.ViewHolder {

        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.error_view_holder, parent, false)

        view.findViewById<Button>(R.id.try_again_button).setOnClickListener {
            this@PagedAdapter.errorState = false
            notifyDataSetChanged()
            loadMoreListener?.onLoadMore()
        }

        return object : RecyclerView.ViewHolder(view) {}
    }

    protected open fun createLoadingView(parent: ViewGroup): RecyclerView.ViewHolder {

        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.loading_view_holder, parent, false)
        return object : RecyclerView.ViewHolder(view) {}
    }

    companion object {
        private const val ITEM_VIEW = 0
        private const val LOADING_VIEW = 1
        private const val ERROR_VIEW = 2
    }

    interface ILoadMoreListener {
        fun onLoadMore()
    }
}