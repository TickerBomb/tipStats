package viewmodel

import CoroutinesComponent
import com.sun.tools.javac.Main
import domain.AppPreferences
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import logger
import model.Cast
import model.DegenPoints
import model.DegenTipStats
import model.User
import network.NetworkRepository
import network.Result

class MainViewModel(
    private val networkRepository: NetworkRepository,
    private val preferences: AppPreferences,
    private val coroutinesComponent: CoroutinesComponent
) {
    private val _uiState = MutableStateFlow(MainUiState())

    val fid = preferences.getFid()
    val userFlow = fid.flatMapConcat {
        logger.info { "fid is $it" }
        networkRepository.getUser(it)
    }
        .onEach {
            logger.info { "result: $it" }

        }
        .filter { it.isSuccess() }
        .map { it.get()!! }

    val degenTipStatsFlow = fid.flatMapConcat {
        networkRepository.getDegenTipStats(it)
    }.map {
        when (it) {
            is Result.Success -> it.data
            else -> DegenTipStats()
        }
    }

    val degenPointsFlow = degenTipStatsFlow.flatMapConcat {
        networkRepository.getDegenPoints(it.walletAddress)
    }.map {
        when (it) {
            is Result.Success -> it.data
            else -> DegenPoints()
        }
    }

    val castsByFidFlow = fid.flatMapConcat {
        networkRepository.getCasts(it)
    }.map {
        when (it) {
            is Result.Success -> it.data.result.casts
            else -> emptyList()
        }
    }

    val uiState = combine(userFlow, degenTipStatsFlow, degenPointsFlow, castsByFidFlow) { user, degenTipStats, degenPoints, casts ->
        MainUiState(user = user, casts = casts, degenTipStats = degenTipStats, points = degenPoints.points)
    }.stateIn(coroutinesComponent.applicationScope, started = SharingStarted.WhileSubscribed(1000), initialValue = MainUiState.Empty)

    fun test() {
        CoroutineScope(Dispatchers.IO).launch {
            networkRepository.getUser(fid = "500169").collect {
//                val userName = it.get()?.result?.get("user")?.username
////                val child = it.get()?.result?.casts?.get(0)?.childCast
//                logger.info { "userName: $userName" }
////                logger.info { "child: $child" }
//
            }
        }
    }

    fun saveFid(input: String) = coroutinesComponent.applicationScope.launch {
        logger.info { "saveFid: $input" }
        if (input.isNotEmpty()) preferences.setFid(input)
    }
}

data class MainUiState(
    val user: User = User(),
    val casts: List<Cast> = emptyList(),
    val degenTipStats: DegenTipStats = DegenTipStats(),
    val points: String = "0"
) {
    companion object {
        val Empty = MainUiState()
    }
}