package com.mcit.newsfinder.global

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mcit.newsfinder.ui.activity.MainActivity
import com.mcit.newsfinder.R
import com.mcit.newsfinder.ui.fragment.news.FilterAdapter
import java.util.*


abstract class BaseFragment : Fragment(){

    lateinit var parent: MainActivity

    abstract fun layoutResource(inflater: LayoutInflater, container: ViewGroup?): View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutResource(inflater, container)
    }
    fun setLocale(localeName: String) {
        val locale = Locale(localeName)
        Locale.setDefault(locale)
        val res = resources
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.locale = locale
        res.updateConfiguration(conf, dm)
    }


    fun showSuccessMessage(titleMessage: Int, message: Int) {
        AlertUtil.showSuccess(
            requireActivity(),
            titleMessage,
            message
        )
    }

    fun showFailedMessage(message: String) {
        AlertUtil.showError(
            requireActivity(),
            R.string.error_title,
            message
        )
    }



    fun showProgress() {
        activity?.let {
            LoaderDialog.getInstance(it).showProgress()
        }
    }

    fun hideProgress() {
        activity?.let {
            LoaderDialog.getInstance(it).hideProgress()
        }
    }


}