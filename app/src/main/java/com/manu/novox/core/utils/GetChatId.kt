package com.manu.novox.core.utils

fun getChatId(p0: String, p1: String) : String{
    return if (p0 > p1) {
        "${p0}_${p1}"
    } else {
        "${p1}_${p0}"
    }
}