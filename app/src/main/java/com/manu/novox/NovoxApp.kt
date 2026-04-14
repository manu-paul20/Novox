package com.manu.novox

import android.app.Application
import com.cloudinary.android.MediaManager
import com.manu.novox.others.MyConstants
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NovoxApp(): Application(){
    override fun onCreate() {
        super.onCreate()
        val config = mapOf("cloud_name" to MyConstants.CLOUDINARY.CLOUD_NAME)
        MediaManager.init(this,config)
    }
}