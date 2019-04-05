package br.com.mxel.cuedot.presentation.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class BaseListViewHolder<T>(
        view: View,
        val loadingMode: Boolean = false
) : RecyclerView.ViewHolder(view){

    var currentItem:T? = null
        protected set

    open fun bindItem(item: T) {}
}