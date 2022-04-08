package ru.cactus.jotme.app

import android.app.Application
import android.content.Context
import ru.cactus.jotme.di.*

class App : Application() {

    lateinit var appComponent: AppComponent
        private set

    lateinit var featureComponent: FeatureComponent
        private set

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder().application(this).build()

        featureComponent = DaggerFeatureComponent.builder().deps(FeatureDepsImpl()).build()

    }

    private inner class FeatureDepsImpl : FeatureDeps {
        override fun context(): Application = this@App
    }
}

val Context.featureComponent: FeatureComponent
    get() = when (this) {
        is App -> featureComponent
        else -> applicationContext.featureComponent
    }