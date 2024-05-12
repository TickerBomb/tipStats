package ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import browseUrl
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import model.Cast
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Composable
fun CastItem(modifier: Modifier = Modifier, cast: Cast) {
    Card(modifier = modifier.padding(vertical = 10.dp)) {
        Column(modifier = Modifier.padding(10.dp).clickable {
            browseUrl(getUrl(cast))
        }) {
            Text("userName: @${cast.author.username}")
            Spacer(modifier = Modifier.height(5.dp))
            Text("text: ${cast.text}")
            Text("createAt: ${getDateTime(cast.timestamp)}")
        }
    }
}

@Composable
internal fun CastItem(modifier: Modifier = Modifier, userName: String, text: String) {

}

private fun getUrl(cast: Cast): String {
    val url = "https://warpcast.com/${cast.author.username}/${cast.hash}"
    return url
}

private fun getDateTime(timestamp: Long): String {
    val instant = Instant.fromEpochMilliseconds(timestamp)
    val localDateTime = instant.toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault())
    val str = DateTimeFormatter
        .ofPattern("yyyy-MM-dd HH:mm", Locale.getDefault())
        .format(localDateTime.toJavaLocalDateTime())
    return str

}