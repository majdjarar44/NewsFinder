package com.mcit.newsfinder.global

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlin.properties.Delegates

abstract class BaseAdapter<M, V : BaseViewHolder<M>> @JvmOverloads constructor(
    var items: MutableList<M>? = mutableListOf()
) : RecyclerView.Adapter<V>() {



    protected var context: Context by Delegates.notNull()
    protected var inflater: LayoutInflater by Delegates.notNull()
    protected var recyclerView: RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
        context = recyclerView.context
        inflater = LayoutInflater.from(context)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        this.recyclerView = null
    }

    override fun getItemCount(): Int = if ((items == null || items?.size == 0)) {
        1
    } else {
        items?.size ?: 0
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): V = getViewHolder(parent, viewType)


    override fun getItemViewType(position: Int): Int {
        if ((items == null || items?.size == 0) ) {
            return EMPTY_TYPE
        }
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: V, position: Int) {
        bind(holder, position)

    }

    open fun bind(holder: V, position: Int) {
        holder.bind(position, items?.getOrNull(position))
    }
    abstract fun getViewHolder(parent: ViewGroup, viewType: Int): V

    fun get(position: Int): M? {
        return items?.getOrNull(position)
    }
    private companion object {
        const val EMPTY_TYPE = 5213
    }
}