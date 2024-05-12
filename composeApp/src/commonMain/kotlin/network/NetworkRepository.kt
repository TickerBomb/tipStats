package network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.flow
import model.ApiResult
import model.Casts
import model.DegenPoints
import model.DegenTipStats
import model.User

class NetworkRepository(private val httpClient: HttpClient) {
    fun getUser(fid: String) = flow {
        runCatching {
            httpClient.get("https://build.far.quest/farcaster/v2/user") {
                headers {
                    append("accept", "application/json")
                    append("API-KEY", "LN626-ICQJB-MJRU6-ZOELF-CVGOU")
                }
                parameter(key = "fid", fid)
            }.body<ApiResult<Map<String, User>>>().result[User::class.simpleName?.lowercase() ?: "user"]!!
        }.onSuccess { emit(Result.Success(it)) }
            .onFailure { emit(Result.Error(it)) }
    }

    fun getCasts(fid: String, limit: Int = 10, cursor: String = "") = flow {
        runCatching {
            httpClient.get("https://build.far.quest/farcaster/v2/casts") {
                headers {
                    append("accept", "application/json")
                    append("API-KEY", "LN626-ICQJB-MJRU6-ZOELF-CVGOU")
                }
                parameter(key = "fid", fid)
                parameter(key = "limit", limit)
            }.body<ApiResult<Casts>>()
        }.onSuccess { emit(Result.Success(it)) }
            .onFailure { emit(Result.Error(it)) }
    }

    fun getDegenTipStats(fid: String)  = flow {
        runCatching {
            httpClient.get("https://www.degen.tips/api/airdrop2/tip-allowance") {
                headers {
                    append("accept", "application/json")
                }
                parameter(key = "fid", fid)
            }.body<List<DegenTipStats>>()[0]
        }.onSuccess { emit(Result.Success(it)) }
            .onFailure { emit(Result.Error(it)) }

    }

    fun getDegenPoints(address: String) = flow {
        runCatching {
            httpClient.get("https://www.degen.tips/api/airdrop2/season4/points") {
                headers {
                    append("accept", "application/json")
                }
                parameter(key = "address", address)
            }.body<List<DegenPoints>>()[0]
        }.onSuccess { emit(Result.Success(it)) }
            .onFailure { emit(Result.Error(it)) }

    }

}