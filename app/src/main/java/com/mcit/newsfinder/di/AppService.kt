package com.mcit.newsfinder.di

import com.mcit.newsfinder.data.model.main.MainNews
import com.mcit.newsfinder.utils.Utils
import com.mcit.newsfinder.utils.Utils.getCurrentDay
import retrofit2.Response
import retrofit2.http.*

interface AppService {


    @GET("v2/everything?q=tesla&sortBy=publishedAt&apiKey=e6ae6a83e0214b319a12478a9d029408")
    suspend fun getSession( @Query("from") from: String): Response<MainNews>
//    @Path("date")date:String
}