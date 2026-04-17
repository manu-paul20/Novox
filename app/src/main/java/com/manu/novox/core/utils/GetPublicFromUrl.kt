package com.manu.novox.core.utils

fun getPublicIdFromUrl(url: String): String{
    return url
        .substringAfterLast("/")
        .substringBeforeLast(".")
}