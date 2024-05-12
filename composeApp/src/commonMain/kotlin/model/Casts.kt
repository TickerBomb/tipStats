package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Casts(val casts: List<Cast> = emptyList())

@Serializable
data class ApiResult<T>(val result: T, val next: String = "")