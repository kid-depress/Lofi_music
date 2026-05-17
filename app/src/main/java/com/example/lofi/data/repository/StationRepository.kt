package com.example.lofi.data.repository

import com.example.lofi.data.model.Category
import com.example.lofi.data.model.Station
import com.example.lofi.data.model.StreamType

object StationRepository {

    val allStations: List<Station> = listOf(
        Station(
            id = "lofi-girl",
            name = "Lofi Girl",
            scene = "学习",
            type = StreamType.BILIBILI,
            url = "https://live.bilibili.com/27519423",
            style1 = "Lofi",
            style2 = "Chill",
            color = 0xFF8B5CF6
        ),
        Station(
            id = "lofi-box",
            name = "Lofi Box",
            scene = "学习",
            type = StreamType.MP3,
            url = "https://boxradio-edge-00.streamafrica.net/lofi",
            style1 = "Lofi",
            style2 = "Chill",
            color = 0xFFA78BFA
        ),
        Station(
            id = "lofi-cafe-studying",
            name = "Lofi Studying",
            scene = "学习",
            type = StreamType.MP3,
            url = "https://radio.loficafe.net/listen/studying/radio.mp3",
            style1 = "Lofi",
            style2 = "Study",
            color = 0xFF3B82F6
        ),
        Station(
            id = "chill-sky",
            name = "Chill Sky",
            scene = "阅读",
            type = StreamType.MP3,
            url = "https://chill.radioca.st/stream",
            style1 = "Chill",
            style2 = "Electro",
            color = 0xFF06B6D4
        ),
        Station(
            id = "lofi-cafe-japanese",
            name = "Lofi Japanese",
            scene = "阅读",
            type = StreamType.MP3,
            url = "https://radio.loficafe.net/listen/japanese-lofi/radio.mp3",
            style1 = "Japanese",
            style2 = "Lofi",
            color = 0xFFF472B6
        ),
        Station(
            id = "jazz-box",
            name = "Jazz Box",
            scene = "阅读",
            type = StreamType.MP3,
            url = "https://boxradio-edge-01.streamafrica.net/jazz",
            style1 = "Jazz",
            style2 = "Smooth",
            color = 0xFFD946EF
        ),
        Station(
            id = "b3cks-radio",
            name = "B3cks Radio",
            scene = "阅读",
            type = StreamType.MP3,
            url = "https://radio.b3ck.com/listen/b3cks-radio/radio.mp3",
            style1 = "Lofi",
            style2 = "Relax",
            color = 0xFFff7096
        ),
        Station(
            id = "chill-wave",
            name = "Chill Wave",
            scene = "放松",
            type = StreamType.MP3,
            url = "https://boxradio-edge-00.streamafrica.net/chillwave",
            style1 = "Chill",
            style2 = "Electro",
            color = 0xFFEC4899
        ),
        Station(
            id = "lofi-cafe-chilling",
            name = "Lofi Chilling",
            scene = "放松",
            type = StreamType.MP3,
            url = "https://radio.loficafe.net/listen/chilling/radio.mp3",
            style1 = "Lofi",
            style2 = "Chill",
            color = 0xFFf65c71
        ),
        Station(
            id = "paradise",
            name = "Paradise",
            scene = "放松",
            type = StreamType.MP3,
            url = "https://stream.radioparadise.com/mellow-128",
            style1 = "Chill",
            style2 = "Alt",
            color = 0xFFF59E0B
        ),
        Station(
            id = "groove-salad",
            name = "Groove Salad",
            scene = "编程",
            type = StreamType.MP3,
            url = "https://ice1.somafm.com/groovesalad-128-mp3",
            style1 = "Chill",
            style2 = "Ambient",
            color = 0xFF10B981
        ),
        Station(
            id = "freecodecamp-coderadio",
            name = "Code Radio",
            scene = "编程",
            type = StreamType.MP3,
            url = "https://coderadio-admin-v2.freecodecamp.org/listen/coderadio/radio.mp3",
            style1 = "Lofi",
            style2 = "Coding",
            color = 0xFF9050b3
        ),
        Station(
            id = "rain-sounds",
            name = "Rain Sounds",
            scene = "助眠",
            type = StreamType.MP3,
            url = "https://boxradio-edge-01.streamafrica.net/rain",
            style1 = "Ambient",
            style2 = "Nature",
            color = 0xFF0EA5E9
        ),
        Station(
            id = "lofi-cafe-sleeping",
            name = "Lofi Sleeping",
            scene = "助眠",
            type = StreamType.MP3,
            url = "https://radio.loficafe.net/listen/sleeping/radio.mp3",
            style1 = "Lofi",
            style2 = "Sleep",
            color = 0xFF498eef
        ),
        Station(
            id = "drone-zone",
            name = "Drone Zone",
            scene = "助眠",
            type = StreamType.MP3,
            url = "https://ice1.somafm.com/dronezone-128-mp3",
            style1 = "Ambient",
            style2 = "Deep",
            color = 0xFF743bed
        ),
        Station(
            id = "asp",
            name = "ASP",
            scene = "助眠",
            type = StreamType.MP3,
            url = "https://radio.stereoscenic.com/asp-s",
            style1 = "Ambient",
            style2 = "Sleep",
            color = 0xFF6366F1
        ),
        Station(
            id = "swiss-classic",
            name = "Swiss Classic",
            scene = "专注",
            type = StreamType.MP3,
            url = "https://stream.srg-ssr.ch/m/rsc_de/mp3_128",
            style1 = "Classical",
            style2 = "Symphony",
            color = 0xFF84CC16
        ),
        Station(
            id = "jazz-groove",
            name = "Jazz Groove",
            scene = "写作",
            type = StreamType.MP3,
            url = "https://west-mp3-128.streamthejazzgroove.com/stream",
            style1 = "Jazz",
            style2 = "Groove",
            color = 0xFFF97316
        ),
        Station(
            id = "jazz-smooth",
            name = "Jazz Smooth",
            scene = "办公",
            type = StreamType.MP3,
            url = "https://smoothjazz.cdnstream1.com/2585_128.mp3",
            style1 = "Jazz",
            style2 = "Mellow",
            color = 0xFFA855F7
        ),
        Station(
            id = "rap",
            name = "Rap Beats",
            scene = "运动",
            type = StreamType.MP3,
            url = "https://boxradio-edge-00.streamafrica.net/rap",
            style1 = "Hip-Hop",
            style2 = "Beats",
            color = 0xFFF43F5E
        ),
        Station(
            id = "lofi-cafe-gaming",
            name = "Lofi Gaming",
            scene = "娱乐",
            type = StreamType.MP3,
            url = "https://radio.loficafe.net/listen/gaming/radio.mp3",
            style1 = "Lofi",
            style2 = "Gaming",
            color = 0xFF22C55E
        )
    )

    val categories: List<Category> = buildList {
        add(Category(id = "all", name = "全部", count = allStations.size))
        val mainScenes = listOf("学习", "编程", "阅读", "放松", "助眠", "专注")
        mainScenes.forEach { scene ->
            val count = allStations.count { it.scene == scene }
            add(Category(id = scene, name = scene, count = count))
        }
        val otherCount = allStations.count { it.scene !in mainScenes }
        if (otherCount > 0) {
            add(Category(id = "其他", name = "其他", count = otherCount))
        }
    }

    fun getFilteredStations(categoryId: String): List<Station> {
        if (categoryId == "all") return allStations
        val mainScenes = listOf("学习", "编程", "阅读", "放松", "助眠", "专注")
        if (categoryId == "其他") return allStations.filter { it.scene !in mainScenes }
        return allStations.filter { it.scene == categoryId }
    }

    fun getStationById(id: String): Station? = allStations.find { it.id == id }

    fun getStationIndex(id: String): Int = allStations.indexOfFirst { it.id == id }

    fun getStationsByScene(scene: String): List<Station> = allStations.filter { it.scene == scene }
}
