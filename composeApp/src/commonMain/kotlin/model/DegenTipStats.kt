package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DegenTipStats(
    @SerialName("snapshot_date")
    val snapshotDate: String = "",
    @SerialName("user_rank")
    val userRank: String = "",
    @SerialName("wallet_address")
    val walletAddress: String = "",
    @SerialName("avatar_url")
    val avatarUrl: String = "",
    @SerialName("display_name")
    val displayName: String = "",
    @SerialName("tip_allowance")
    val tipAllowance: String = "",
    @SerialName("remaining_allowance")
    val remainingAllowance: String = "",
)

@Serializable
data class DegenPoints(val username: String = "", val points: String = "0")