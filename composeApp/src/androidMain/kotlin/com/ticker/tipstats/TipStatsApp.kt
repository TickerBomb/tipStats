package com.ticker.tipstats

import ApplicationComponent
import android.app.Application

class TipStatsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        ApplicationComponent.init()
    }
}