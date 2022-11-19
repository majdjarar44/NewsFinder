package com.mcit.newsfinder.data.repository

import com.mcit.newsfinder.di.DataService
import com.mcit.newsfinder.usecase.BaseDataSource
import javax.inject.Inject

class NewsDataRepository@Inject constructor(val appService: DataService) : BaseDataSource() {
    suspend fun getDataNews() = getResult {
        appService.getDataNewsSession()
    }
}
