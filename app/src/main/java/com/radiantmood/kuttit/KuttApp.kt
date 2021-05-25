package com.radiantmood.kuttit

import android.app.Application

lateinit var kuttApp: KuttApp
    private set

class KuttApp : Application() {
    override fun onCreate() {
        super.onCreate()
        kuttApp = this
    }
}