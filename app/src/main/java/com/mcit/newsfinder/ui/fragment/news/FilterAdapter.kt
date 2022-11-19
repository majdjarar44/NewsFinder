package com.mcit.newsfinder.ui.fragment.news

import android.view.ViewGroup
import com.dominate.talabyeh.app.core.base.BaseAdapter
import com.mcit.newsfinder.databinding.RowFilterBinding
import com.mcit.newsfinder.global.BaseBindingViewHolder
import java.util.ArrayList

class FilterAdapter(private val categoriesList: ArrayList<String>,

private val onclickListener: (((String?)) -> Unit)
) : BaseAdapter<String, FilterAdapter.ViewHolder>(categoriesList) {

    override fun getViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowFilterBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: RowFilterBinding) :
        BaseBindingViewHolder<String>(binding) {

        override fun bind(position: Int, item: String?) {
           binding.item = item
            binding.root.setOnClickListener {
                onclickListener(item)
            }
        }


    }
}