package br.com.mxel.cuedot.presentation.base

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.core.widget.ContentLoadingProgressBar
import androidx.recyclerview.widget.RecyclerView

open class BaseViewHolder<T>(
        view: View
) : RecyclerView.ViewHolder(
        prepareView(view)
) {

    lateinit var container: FrameLayout

    lateinit var loading: ContentLoadingProgressBar

    var currentItem: T? = null
        protected set(value) {
            field = value
            showLoading(field == null)
        }

    open fun bindItem(item: T?) {}

    protected fun showLoading(show: Boolean) {

        if (show) {
            itemView.findViewById<View>(CONTAINER_ID).visibility = View.GONE
            itemView.findViewById<View>(LOADER_ID).visibility = View.VISIBLE
        } else {
            itemView.findViewById<View>(CONTAINER_ID).visibility = View.VISIBLE
            itemView.findViewById<View>(LOADER_ID).visibility = View.GONE
        }
    }

    companion object {

        protected const val CONTAINER_ID = 1
        protected const val LOADER_ID = 2

        fun prepareView(view: View): View {

            val fullView = FrameLayout(view.context).apply {
                layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            }

            fullView.addView(view.apply { id = CONTAINER_ID })
            fullView.addView(ProgressBar(view.context).apply { id = LOADER_ID })

            return fullView
        }
    }
}