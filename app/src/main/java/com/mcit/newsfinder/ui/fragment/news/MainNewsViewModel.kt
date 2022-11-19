package com.mcit.news

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcit.newsfinder.data.model.MixedNewsDataModel
import com.mcit.newsfinder.data.model.details.DetailsNewsResponse
import com.mcit.newsfinder.usecase.Resource
import com.mcit.newsfinder.data.model.main.MainNews
import com.mcit.newsfinder.data.repository.MainNewsRepository
import com.mcit.newsfinder.data.repository.NewsDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import okhttp3.Dispatcher
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


@HiltViewModel
class MainNewsViewModel @Inject constructor(
    val mainNews: MainNewsRepository,
    val dataNews: NewsDataRepository
) : ViewModel() {
    val mainNewsResponse = MutableLiveData<Resource<MainNews>>()
    val dataNewsResponse = MutableLiveData<Resource<DetailsNewsResponse>>()
    val mixedNewsDataModel = MutableLiveData<ArrayList<MixedNewsDataModel>>()
    val filterResult = MutableLiveData<ArrayList<MixedNewsDataModel>>()

    init {
        getMainNews(getCurrentDay())
    }

    fun getMainNews(format: String) {
        viewModelScope.launch {
            viewModelScope.async(Dispatchers.IO) {
                mainNewsResponse.postValue(mainNews.getMainNews(format))
                dataNewsResponse.postValue(dataNews.getDataNews())
            }.await()
            getMixData()
        }
    }

    fun getMixData() {
        var arrayListNews: ArrayList<MixedNewsDataModel> = arrayListOf()
        mainNewsResponse.value?.data?.articles?.map {
            val mix = MixedNewsDataModel(
                it.author, it.description, it.publishedAt, it.title, it.url, it.urlToImage ?: "",
                arrayListOf(), arrayListOf()
            )
            arrayListNews.add(mix)
        }
        dataNewsResponse.value?.data?.results?.map {
            val mix =
                MixedNewsDataModel(
                    it.creator?.get(0) ?: " ",
                    it.description,
                    it.pubDate,
                    it.title,
                    it.link,
                    it.image_url ?: "",
                    it.category,
                    it.country
                )

            if (mix != null) {
                arrayListNews.add(mix)
            }

        }

        mixedNewsDataModel.postValue(arrayListNews)
    }

    fun getCurrentDay(): String {
        val c = Calendar.getInstance().time
        println("Current time => $c")
        val df = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        return df.format(c)
    }

    fun filterByCountry(country: String) {
        var arrayListNews: ArrayList<MixedNewsDataModel> = arrayListOf()
        mixedNewsDataModel.value?.forEach {
            if (it.country.isNotEmpty()) {
                if (it.country[0] == country) {
                    arrayListNews.add(it)
                }
            }
            filterResult.postValue(arrayListNews)
        }
    }

    fun filterByCategory(category: String) {
        var arrayListNews: ArrayList<MixedNewsDataModel> = arrayListOf()
        mixedNewsDataModel.value?.forEach {
            if (it.category.isNotEmpty()) {
                if (it.country[0] == category) {
                    arrayListNews.add(it)
                }
            }
            filterResult.postValue(arrayListNews)
        }

    }
}