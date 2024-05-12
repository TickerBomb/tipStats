package com.ticker.tipstats

import android.content.Context
import androidx.startup.Initializer

internal lateinit var applicationContext: Context
    private set

class ContextProvider : Initializer<Context> {
    override fun create(context: Context): Context {
        applicationContext = context.applicationContext
        return applicationContext
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}