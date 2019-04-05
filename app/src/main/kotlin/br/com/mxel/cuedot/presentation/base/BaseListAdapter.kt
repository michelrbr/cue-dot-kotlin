package br.com.mxel.cuedot.presentation.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.mxel.cuedot.R

abstract class BaseListAdapter<T>(
        diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, BaseListViewHolder<T>>(diffCallback) {

    var loading: Boolean = false
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

    override fun getItemViewType(position: Int): Int {
        return if (loadEnable && position > itemCount - 2) LOADING_VIEW else ITEM_VIEW
    }

    override fun getItemCount(): Int {

        return super.getItemCount().let {
            if (loadEnable && it > 0) {
                it + 1
            } else {
                it
            }
        }
    }

    override fun submitList(list: List<T>?) {
        loading = false
        super.submitList(list)
    }

    protected fun buildLoadingView(parent: ViewGroup): BaseListViewHolder<T> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.loading_view_holder, parent, false)
        return BaseListViewHolder(view, true)
    }

    companion object {
        const val ITEM_VIEW = 0
        const val LOADING_VIEW = 1
    }

    interface ILoadMoreListener {
        fun onLoadMore()
    }
}