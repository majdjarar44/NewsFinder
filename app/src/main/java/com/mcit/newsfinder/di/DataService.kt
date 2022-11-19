package com.mcit.newsfinder.di

import com.mcit.newsfinder.data.model.details.DetailsNewsResponse
import com.mcit.newsfinder.data.model.main.MainNews
import retrofit2.Response
import retrofit2.http.GET

interface DataService {
    @GET("api/1/news?apikey=pub_1508e9913474fcb85d810a8f1126e1a43e04&q=cryptocurrency")
    suspend fun getDataNewsSession(): Response<DetailsNewsResponse>
}