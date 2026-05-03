package com.example.crypticgame.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.crypticgame.ui.theme.AccentPrimary
import com.example.crypticgame.ui.theme.ShareTechMono

@Composable
fun TerminalKeyboard(
    onKeyPress: (String) -> Unit,
    onBackspace: () -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val rows = listOf(
        listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"),
        listOf("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"),
        listOf("A", "S", "D", "F", "G", "H", "J", "K", "L"),
        listOf("Z", "X", "C", "V", "B", "N", "M")
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        rows.forEachIndexed { index, row ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                if (index == 2) Spacer(modifier = Modifier.weight(0.5f))
                if (index == 3) Spacer(modifier = Modifier.weight(1.5f))
                row.forEach { key ->
                    TerminalKey(
                        text = key,
                        modifier = Modifier.weight(1f),
                        onClick = { onKeyPress(key.uppercase()) }
                    )
                }
                if (index == 2) Spacer(modifier = Modifier.weight(0.5f))
                if (index == 3) Spacer(modifier = Modifier.weight(1.5f))
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 2.dp)
        ) {
            TerminalKey(
                text = "< DEL",
                modifier = Modifier.weight(1.5f),
                onClick = onBackspace
            )
            TerminalKey(
                text = "SPACE",
                modifier = Modifier.weight(2f),
                onClick = { onKeyPress(" ") }
            )
            TerminalKey(
                text = "EXEC >",
                modifier = Modifier.weight(1.5f),
                onClick = onSubmit
            )
        }
    }
}

@Composable
fun TerminalKey(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .height(40.dp)
            .border(1.dp, AccentPrimary.copy(alpha = 0.4f))
            .background(AccentPrimary.copy(alpha = 0.08f))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium.copy(
                color = AccentPrimary,
                fontFamily = ShareTechMono,
                fontSize = 16.sp
            )
        )
    }
}