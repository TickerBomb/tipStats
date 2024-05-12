package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val fid: String = "",
    val followingCount: Long = 0L,
    val followerCount: Long = 0L,
    val pfp: Pfp = Pfp(),
    val connectedAddress: String? = "",
    val username: String = "",
    val displayName: String = ""
)

@Serializable
data class Pfp(val url: String = "", val verified: Boolean = false)