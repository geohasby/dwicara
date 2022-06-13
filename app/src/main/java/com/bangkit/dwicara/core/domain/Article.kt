package com.bangkit.dwicara.core.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val img_url: String,
    val title: String,
    val date: String,
    val source: String,
    val content: String
) : Parcelable
