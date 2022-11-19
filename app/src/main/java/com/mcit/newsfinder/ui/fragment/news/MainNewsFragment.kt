package com.mcit.newsfinder.ui.fragment.news

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.mcit.newsfinder.R
import com.mcit.newsfinder.data.model.MixedNewsDataModel
import com.mcit.newsfinder.databinding.FragmentMainNewsBinding
import com.mcit.newsfinder.global.BaseFragment
import com.mcit.newsfinder.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlin.collections.ArrayList


@AndroidEntryPoint
class MainNewsFragment : BaseFragment() {

    private lateinit var binding: FragmentMainNewsBinding
    private val viewModel: MainNewsViewModel by viewModels()
    private var isLoading: Boolean = false
    private var handler: Handler = Handler()
    private var newsDataList = mutableListOf<MixedNewsDataModel>()

    override fun layoutResource(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentMainNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout()
        observeNewNews()
        setHasOptionsMenu(true)
        binding.progressParLoading.visibility = View.VISIBLE
        initialFilter()
    }

    private fun initialPagination(layoutManager: LinearLayoutManager) {
        binding.recMainNews.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!isLoading) {
                    if (layoutManager.findLastCompletelyVisibleItemPosition() == viewModel.mixedNewsDataModel.value?.size!! - 1) {
                        loadData()
                        isLoading = true
                    }
                }
            }
        })
    }

    fun loadData(isInitLoad: Boolean = false) {
        binding.llLoader.visibility = View.VISIBLE
        if (isInitLoad) {
            (0..10).mapTo(newsDataList) { newsDataList[it] }
            initialAdapter()
            binding.llLoader.visibility = View.GONE
        } else {
            handler.postDelayed(Runnable {
                val start = newsDataList.size
                val end = start + 10
                val newList = ArrayList<MixedNewsDataModel>()
                if (end < newsDataList.size) {
                    (start..end).mapTo(newList) { newList[it] }
                    updateDataList(newList)
                    isLoading = false
                }
                binding.llLoader.visibility = View.GONE
            }, 2000)
        }
    }

    private fun initialAdapter() {
        binding.recMainNews.adapter =
            MainNewsAdapter(newsDataList as ArrayList<MixedNewsDataModel>) { url ->
                goToUrl(url)
            }
    }

    fun updateDataList(newList: List<MixedNewsDataModel>) {
        val tempList = newsDataList.toMutableList()
        tempList.addAll(newList)
        binding.recMainNews.adapter =
            MainNewsAdapter(tempList as ArrayList<MixedNewsDataModel>) { url ->
                goToUrl(url)
            }
        newsDataList = tempList
    }

    private fun goToUrl(url: String?) {
        if (url?.isNotEmpty() == true) {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        } else {
            showFailedMessage("No Have Url")
        }
    }

    private fun initialFilter() {
        viewModel.filterResult.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                setArticlesInAdapter()
            } else {
                binding.recMainNews.visibility = View.GONE
                binding.tvNoDataAvailable.visibility = View.VISIBLE
            }
        })
    }

    private fun observeNewNews() {
        viewModel.mixedNewsDataModel.observe(viewLifecycleOwner, Observer {
            newsDataList = it
            setArticlesInAdapter()
            binding.progressParLoading.visibility = View.GONE
        })
    }

    private fun setArticlesInAdapter() {
        binding.recMainNews.visibility = View.VISIBLE
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recMainNews.layoutManager = layoutManager
        loadData(isInitLoad = true)
        initialPagination(layoutManager)
    }

    private fun swipeRefreshLayout() {
        binding?.srRefreshLayout?.setOnRefreshListener {
            binding?.srRefreshLayout?.isRefreshing = false
            viewModel.getMainNews(Utils.getCurrentDay())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_country -> {
                showAlertDialog(
                    arrayListOf(
                        "united states of america",
                        "united arab emirates",
                        "Jordan"
                    ), true
                )
                true
            }
            R.id.action_category -> {
                showAlertDialog(arrayListOf("sports", "business", "health"), false)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showAlertDialog(arrayList: ArrayList<String>, isFilterByCountry: Boolean) {
        val dialogBuilder = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.row_filter_dialog, null)
        dialogBuilder.setView(dialogView)

        val rvItem = dialogView.findViewById(R.id.rvItem) as RecyclerView
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
        alertDialog.window?.setGravity(Gravity.CENTER)
        alertDialog.window?.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
        )

        var adapter = FilterAdapter(arrayList) {
            if (isFilterByCountry) {
                viewModel.filterByCountry(it.toString())
            } else {
                viewModel.filterByCategory(it.toString())
            }
            alertDialog.dismiss()
        }

        rvItem.adapter = adapter
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rvItem?.layoutManager = layoutManager
    }

}