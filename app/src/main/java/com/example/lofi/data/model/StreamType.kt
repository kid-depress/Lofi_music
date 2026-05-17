package com.example.lofi.data.model

enum class StreamType {
    MP3,
    M3U8,
    BILIBILI;

    companion object {
        fun fromString(value: String): StreamType = when (value.lowercase()) {
            "mp3" -> MP3
            "m3u8" -> M3U8
            "bilibili" -> BILIBILI
            else -> MP3
        }
    }
}
