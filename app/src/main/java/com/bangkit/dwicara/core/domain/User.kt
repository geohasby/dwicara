package com.bangkit.dwicara.core.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String?,
    val name: String?,
    val photo_url: String?,
    val status: String?,
    val native: String?,
    val learn: String?
): Parcelable
