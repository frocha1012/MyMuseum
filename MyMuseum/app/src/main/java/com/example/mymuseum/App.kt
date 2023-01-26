package com.example.mymuseum

import android.app.Application

class App : Application() {
    companion object {// 伴生对象
    lateinit var instance: App
        private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
