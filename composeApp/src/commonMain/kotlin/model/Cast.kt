package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Cast(
    val hash: String,
    val parentUrl: String = "",
    val threadHash: String = "",
    val text: String = "",
    val author: Author = Author(),
    val parentAuthor: Author? = Author(),
    val timestamp: Long = 0L,
    val channel: Channel? = Channel()
//    val childrenCasts: List<Cast> = emptyList()
) {
    @SerialName("childCast")
    var childCast: Cast? = null
}


@Serializable
data class Channel(
    @SerialName("_id")
    val id: String = "",
    val channelId: String = "",
    val name: String = "",
    val url: String = ""
)
