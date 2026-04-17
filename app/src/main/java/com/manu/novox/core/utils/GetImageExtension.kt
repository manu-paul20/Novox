package com.manu.novox.core.utils

fun getImageExtension(url: String): String{
    return url.substringAfterLast(".","jpg")
}