package com.kriticalflare.nativbin

import android.app.Application
import com.airbnb.mvrx.Mavericks
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MitbinApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Mavericks.initialize(debugMode = true)
    }
}
