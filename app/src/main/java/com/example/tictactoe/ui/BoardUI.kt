package com.example.tictactoe.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tictactoe.ui.theme.TicTacToeTheme


@Composable
fun GameBoard() {
    val viewModel: TTTViewModel = viewModel()
    var click by remember { mutableStateOf("Nothing") }
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = click)
            Text(text = viewModel.error.value.value, color = Color.Red)
            for (i in 0..2) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    for (j in 0..2) {
                        ButtonUi(
                            rowState = j,
                            columnState = i,
                            clickEvent = { a, b ->
                                click = "Row = $a & Column = $b"
                                viewModel.checkValue(b, a)
                            },
                            value = viewModel.sendValue(i, j).term,
                            bg = viewModel.sendValue(i, j).color
                                ?: MaterialTheme.colorScheme.background.copy(alpha = 0.0f),
                            clickable = viewModel.clickable.value.value
                        )
                    }
                }
            }
            ExtendedFloatingActionButton(onClick = { viewModel.reset() }) {
                Icon(Icons.Rounded.Refresh, contentDescription = "Refresh")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ButtonUi(
    rowState: Int,
    columnState: Int,
    clickEvent: (Int, Int) -> Unit,
    value: String,
    bg: Color?,
    clickable: Boolean
) {
    Card(
        onClick = { clickEvent(rowState, columnState) },
        modifier = Modifier.size(64.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        enabled = clickable
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .background(bg!!), contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                fontSize = 48.sp,
                textAlign = TextAlign.Center,
                lineHeight = 0.sp
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TicTacToeTheme {
        GameBoard()
    }
}

