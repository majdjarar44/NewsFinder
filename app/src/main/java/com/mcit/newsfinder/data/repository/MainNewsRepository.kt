package com.mcit.newsfinder.data.repository

import android.util.Log
import com.mcit.newsfinder.di.AppService
import com.mcit.newsfinder.usecase.BaseDataSource
import javax.inject.Inject

class MainNewsRepository @Inject constructor(val appService: AppService) : BaseDataSource() {
    suspend fun getMainNews(dayOfMonth: String) = getResult {
        appService.getSession(dayOfMonth)
    }
}