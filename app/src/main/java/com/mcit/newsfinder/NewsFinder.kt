package com.mcit.newsfinder

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NewsFinder :Application(){
    override fun onCreate() {
        super.onCreate()
    }
}