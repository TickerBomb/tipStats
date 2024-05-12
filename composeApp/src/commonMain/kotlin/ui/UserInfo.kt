package ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Shapes
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalPlatformTextInputMethodOverride
import androidx.compose.ui.unit.dp
import coil3.BitmapImage
import coil3.Image
import model.User
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import model.DegenTipStats
import org.jetbrains.skia.Bitmap


@Composable
fun UserInfo(modifier: Modifier = Modifier, user: User, degenTipStats: DegenTipStats, points: String) {
    val model = ImageRequest.Builder(PlatformContext.INSTANCE).data(user.pfp.url).build()
    Row(modifier = modifier) {

        AsyncImage(
            modifier = Modifier.size(50.dp).clip(CircleShape),
            model = model,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
            Text("@${user.username}")
            Row {
                Text("Rank: ${degenTipStats.userRank}")
                Spacer(modifier = Modifier.width(20.dp))
                Text("Fid: ${user.fid}")
                Spacer(modifier = Modifier.width(20.dp))
                Text("Points: $points")
            }
        }
    }

}