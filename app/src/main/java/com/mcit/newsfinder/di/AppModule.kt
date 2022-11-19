package com.mcit.newsfinder.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mcit.newsfinder.BuildConfig

import com.mcit.newsfinder.di.DataService
import com.mcit.newsfinder.global.Qualifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    @Qualifier.Main
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URl)
        .client(getLanguageHeaderOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()



    @Singleton
    @Provides
    @Qualifier.Data
    fun provideRetrofitDataNews(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URl1)
        .client(getLanguageHeaderOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    //Build Api services with respect to qualifiers

    @Provides
    @Singleton
    fun authApiService(@Qualifier.Main retrofit: Retrofit): AppService = retrofit.create(AppService::class.java)
//
//
    @Provides
    @Singleton
    fun settingApiService(@Qualifier.Data retrofit: Retrofit): DataService = retrofit.create(DataService::class.java)
//

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()


//    @Provides
//    fun provideMainNewsService(retrofit: Retrofit): AppService =
//        retrofit.create(AppService::class.java)

    @Provides
    @Singleton
    fun getLanguageHeaderOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.HEADERS
            this.level = HttpLoggingInterceptor.Level.BODY
        })
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
        return builder.build()
    }
}