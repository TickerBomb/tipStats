package model

import kotlinx.serialization.Serializable

@Serializable
data class Author(
    val fid: String = "",
    val followingCount: Long = 0,
    val followerCount: Long = 0,
    val displayName: String = "",
    val username: String = ""
)