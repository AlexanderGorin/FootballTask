package com.alexandergorin.footballtask

import android.app.Application
import com.alexandergorin.footballtask.di.AppComponent
import com.alexandergorin.footballtask.di.DaggerAppComponent

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}
