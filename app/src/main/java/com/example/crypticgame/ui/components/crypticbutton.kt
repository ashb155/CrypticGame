import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.crypticgame.ui.theme.AccentPrimary
import com.example.crypticgame.ui.theme.BackgroundDark
import com.example.crypticgame.ui.theme.LockedGray
import com.example.crypticgame.ui.theme.TextDim

@Composable
fun CrypticButton(
    label: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    val borderColor = if (enabled) AccentPrimary else LockedGray
    val textColor = if (enabled) AccentPrimary else TextDim
    var pressed by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .width(220.dp)
            .border(1.dp, borderColor)
            .background(if (pressed && enabled) AccentPrimary else Color.Transparent)
            .clickable(enabled = enabled) {
                pressed = true
                onClick()
            }
            .padding(vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 22.sp,
                color = if (pressed && enabled) BackgroundDark else textColor,
                letterSpacing = 6.sp
            )
        )
    }
}
