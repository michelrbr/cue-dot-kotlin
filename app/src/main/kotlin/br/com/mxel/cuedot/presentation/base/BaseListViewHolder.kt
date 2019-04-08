package br.com.mxel.cuedot.presentation.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class BaseListViewHolder<T>(
        view: View
) : RecyclerView.ViewHolder(view){

    var currentItem:T? = null
        protected set

    open fun bindItem(item: T?) {}
}