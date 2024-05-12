package ui

import Greeting
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PlatformImeOptions
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import logger
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.getKoin
import tipstats.composeapp.generated.resources.Res
import tipstats.composeapp.generated.resources.compose_multiplatform
import viewmodel.MainUiState
import viewmodel.MainViewModel

@OptIn(ExperimentalResourceApi::class)
@Composable
fun MainScreen() {
    var showContent by remember { mutableStateOf(false) }
    val viewModel: MainViewModel = getKoin().get()
    val fid by viewModel.fid.collectAsState(initial = "")
    var input by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()
    Column(Modifier.fillMaxWidth()) {
        Text(
            text = "Today's date is ${todaysDate()}",
            modifier = Modifier.padding(20.dp),
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
        Text("fid: $fid")
        Row(modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 20.dp)) {
            OutlinedTextField(
                modifier = Modifier.onPreviewKeyEvent {
                    if (it.key == Key.Enter) {
                        logger.info { "enter key" }
                        viewModel.saveFid(input)
                        return@onPreviewKeyEvent true
                    }
                    false
                },
                value = input,
                onValueChange = { text -> input = text },
                label = {
                    Text("put fid")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                    platformImeOptions = PlatformImeOptions()
                ),
                keyboardActions = KeyboardActions(onDone = {
                    logger.info { "onDone" }

                }, onSend = {
                    logger.info { "onSend" }

                }),
            )
            Button(modifier = Modifier.align(Alignment.CenterVertically).padding(start = 10.dp), onClick = {
                viewModel.saveFid(input)

            }) {
                Text("Done")
            }

        }
        if (uiState != MainUiState.Empty) {
            UserInfo(
                modifier = Modifier.align(Alignment.Start).padding(start = 20.dp, bottom = 20.dp),
                user = uiState.user,
                degenTipStats = uiState.degenTipStats,
                points = uiState.points
            )

            LazyColumn(modifier = Modifier.padding(horizontal = 20.dp)) {
                items(uiState.casts) {
                    CastItem(cast = it)
                }
            }
        }

        /**
        AnimatedVisibility(showContent) {
            val greeting = remember { Greeting().greet() }
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Image(painterResource(Res.drawable.compose_multiplatform), null)
                Text("Compose: $greeting")
            }
        }
        **/
    }

}

fun todaysDate(): String {
    fun LocalDateTime.format() = toString().substringBefore('T')

    val now = Clock.System.now()
    val zone = TimeZone.currentSystemDefault()
    return now.toLocalDateTime(zone).format()
}
