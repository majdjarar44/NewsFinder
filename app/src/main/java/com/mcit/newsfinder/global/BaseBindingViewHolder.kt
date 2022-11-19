package com.mcit.newsfinder.global

import androidx.databinding.ViewDataBinding
import com.dominate.talabyeh.app.core.base.BaseViewHolder

abstract class BaseBindingViewHolder<M>(viewDataBinding: ViewDataBinding) : BaseViewHolder<M>(viewDataBinding.root) {

    var viewDataBinding: ViewDataBinding? = viewDataBinding


    @Suppress("UNCHECKED_CAST")
    inline fun <T : ViewDataBinding> bind(binding: T.() -> Unit) {
        binding(viewDataBinding as T)

            viewDataBinding?.executePendingBindings()
    }


}
