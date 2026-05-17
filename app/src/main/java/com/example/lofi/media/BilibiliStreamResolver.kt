package com.example.lofi.media

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

object BilibiliStreamResolver {

    private const val API_URL = "https://api.live.bilibili.com/room/v1/Room/playUrl"
    private const val REFERER = "https://live.bilibili.com"

    suspend fun resolveStreamUrl(livePageUrl: String): String? = withContext(Dispatchers.IO) {
        try {
            val roomId = extractRoomId(livePageUrl) ?: return@withContext null
            val apiUrl = "${API_URL}?cid=${roomId}&platform=web&qn=0"
            val connection = URL(apiUrl).openConnection() as HttpURLConnection
            connection.apply {
                setRequestProperty("Referer", REFERER)
                setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                connectTimeout = 8000
                readTimeout = 8000
            }

            val response = connection.inputStream.bufferedReader().readText()
            connection.disconnect()

            val json = JSONObject(response)
            if (json.optInt("code") != 0) return@withContext null

            val data = json.optJSONObject("data") ?: return@withContext null
            val durl = data.optJSONArray("durl") ?: return@withContext null
            if (durl.length() == 0) return@withContext null

            durl.getJSONObject(0).optString("url").takeIf { it.isNotEmpty() }
        } catch (e: Exception) {
            null
        }
    }

    private fun extractRoomId(url: String): String? {
        val regex = Regex("""live\.bilibili\.com/(\d+)""")
        return regex.find(url)?.groupValues?.get(1)
    }
}
