package com.mcit.newsfinder.di

import com.mcit.newsfinder.data.model.main.MainNews
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface AppService {

    @GET("v2/everything?q=tesla&from=2022-10-18&sortBy=publishedAt&apiKey=e6ae6a83e0214b319a12478a9d029408")
    suspend fun getSession(): Response<MainNews>

}