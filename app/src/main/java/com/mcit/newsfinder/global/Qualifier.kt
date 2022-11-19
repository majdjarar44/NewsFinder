package com.mcit.newsfinder.global

class Qualifier {
    @javax.inject.Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class Main

    @javax.inject.Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class Data
}