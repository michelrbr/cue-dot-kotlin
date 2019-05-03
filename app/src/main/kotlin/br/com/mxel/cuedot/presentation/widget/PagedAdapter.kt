package br.com.mxel.cuedot.presentation.widget

import androidx.recyclerview.widget.*
import br.com.mxel.cuedot.presentation.base.BaseViewHolder
import io.reactivex.disposables.CompositeDisposable

abstract class PagedAdapter<T, VH : BaseViewHolder<T>> : RecyclerView.Adapter<VH> {

    private val helper: AsyncListDiffer<T>

    protected val disposable = CompositeDisposable()

    protected var loading: Boolean = false

    var loadEnable: Boolean = true

    var loadMoreListener: ILoadMoreListener? = null

    private var scrollListener =
            object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager

                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    if (
                            !loading &&
                            loadEnable &&
                            (visibleItemCount + firstVisibleItemPosition) >= (totalItemCount - 5)
                    ) {
                        loading = true
                        loadMoreListener?.onLoadMore()
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

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addOnScrollListener(scrollListener)
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
        if (loadEnable && list.last() != null) {
            helper.submitList(ArrayList(list).apply { add(null) })
        } else {
            helper.submitList(list)
        }
    }

    protected fun getItem(position: Int): T {
        return helper.currentList[position]
    }

    override fun getItemCount(): Int {
        return helper.currentList.size
    }

    interface ILoadMoreListener {
        fun onLoadMore()
    }
}