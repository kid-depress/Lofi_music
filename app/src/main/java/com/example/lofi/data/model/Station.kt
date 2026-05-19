package com.example.lofi.data.model

import androidx.compose.runtime.Immutable

@Immutable
data class Station(
    val id: String,
    val name: String,
    val scene: String,
    val type: StreamType,
    val url: String,
    val style1: String,
    val style2: String,
    val description: String? = null,
    val custom: String? = null,
    val color: Long
)
