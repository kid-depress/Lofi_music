package com.example.lofi.data.model

import androidx.compose.runtime.Immutable

@Immutable
data class Category(
    val id: String,
    val name: String,
    val count: Int
)
