package br.com.mxel.cuedot.presentation.base

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseListAdapter<T>(
        diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, BaseListViewHolder<T>>(diffCallback) {

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
                            visibleItemCount + firstVisibleItemPosition >= totalItemCount
                    ) {
                        loading = true
                        loadMoreListener?.onLoadMore()
                    }
                }
            }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addOnScrollListener(scrollListener)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {

        recyclerView.removeOnScrollListener(scrollListener)
        super.onDetachedFromRecyclerView(recyclerView)
    }

    override fun submitList(list: List<T>?) {

        if (list == null) {
            super.submitList(null)
            return
        }

        if (list.isNotEmpty()) {
            loading = false
            if (loadEnable) {
                super.submitList(ArrayList(list).apply { add(null) })
            } else {
                super.submitList(list)
            }
        } else {
            super.submitList(list)
        }
    }

    interface ILoadMoreListener {
        fun onLoadMore()
    }
}