package com.mcit.newsfinder.ui.fragment.news

import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.mcit.newsfinder.R
import com.mcit.newsfinder.data.model.MixedNewsDataModel
import com.mcit.newsfinder.global.BaseBindingViewHolder
import com.mcit.newsfinder.databinding.RowNewsBinding
import com.mcit.newsfinder.global.BaseAdapter

class MainNewsAdapter(
    private val mainNews: ArrayList<MixedNewsDataModel>,
    private val onclickListener: ((String?) -> Unit),
) : BaseAdapter<MixedNewsDataModel, MainNewsAdapter.ViewHolder>(mainNews) {
    override fun getViewHolder(parent: ViewGroup, viewType: Int): MainNewsAdapter.ViewHolder {
        val binding = RowNewsBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: RowNewsBinding) :
        BaseBindingViewHolder<MixedNewsDataModel>(binding) {
        override fun bind(position: Int, item: MixedNewsDataModel?) {
            binding.artical = item
            setImageUrl(binding.imageView,item?.urlToImage)
            binding.root.setOnClickListener {
                    onclickListener(item?.url)

            }
        }
    }
    @BindingAdapter("imageUrl")
    fun setImageUrl(imgView: ImageView, imgUrl: String?) {
        imgUrl?.let {
            val imgUri = it.toUri().buildUpon().scheme("http").build()
            Glide.with(imgView.context)
                .load(imgUri)
                .placeholder(R.drawable.images_news_placholder)
                .into(imgView)
        }
    }
}
