package com.manu.novox.core.utils

import androidx.core.net.toUri
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.manu.novox.others.MyConstants
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

suspend fun uploadToCloudinary(
    photoUrl: String,
    onProgress:((String)-> Unit)?
    ): String {
    return suspendCancellableCoroutine { continuation ->
        val request = MediaManager.get()
            .upload(photoUrl.toUri())
            .unsigned(MyConstants.CLOUDINARY.UPLOAD_PRESET)
            .callback(object : UploadCallback {
                override fun onStart(p0: String?) {

                }

                override fun onProgress(p0: String?, p1: Long, p2: Long) {
                    val percentage = ((p1.toDouble()/p2.toDouble())*100).toInt()
                    onProgress?.invoke(percentage.toString())
                }

                override fun onSuccess(p0: String?, p1: Map<*, *>?) {
                    val profilePicture = p1?.get("secure_url").toString()
                    if (continuation.isActive) {
                        continuation.resume(profilePicture)
                    }
                }

                override fun onError(
                    p0: String?,
                    p1: ErrorInfo?
                ) {
                    if(continuation.isActive){
                        continuation.resumeWithException(Exception(
                            p1?.description?:"Something went wrong"
                        ))
                    }
                }

                override fun onReschedule(
                    p0: String?,
                    p1: ErrorInfo?
                ) {

                }
            }).dispatch()
        continuation.invokeOnCancellation {
            MediaManager.get().cancelRequest(request)
        }
    }
}