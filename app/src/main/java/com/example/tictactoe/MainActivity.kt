package com.example.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tictactoe.ui.theme.TicTacToeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TicTacToeTheme {
                Scaffold() { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        TicTacToeApp()
                    }

                }
            }
        }
    }
}

@Composable
fun TicTacToeApp() {

    Column(){
        GameStats()
        GameBoard()
    }

}

@Preview(showBackground = true)
@Composable
fun TicTacToePreview() {
    TicTacToeTheme {
        TicTacToeApp()
    }
}

@Composable
fun GameStats() {
    Surface(Modifier.fillMaxWidth()) {
        Row(Modifier.padding(top = 25.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painterResource(R.drawable.ic_circle),
                    contentDescription = "noughts",
                    Modifier.size(40.dp),
                    tint = Color.Blue
                )
                Spacer(Modifier.padding(top = 5.dp))
                Text(
                    text = "4 wins",
                    color = Color.Blue,
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painterResource(R.drawable.ic_cross),
                    contentDescription = "noughts",
                    Modifier.size(40.dp),
                    tint = Color.Blue
                )
                Spacer(Modifier.padding(top = 5.dp))
                Text(
                    text = "4 wins",
                    color = Color.Blue,
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painterResource(R.drawable.ic_balance),
                    contentDescription = "noughts",
                    Modifier.size(40.dp),
                    tint = Color.DarkGray.copy(alpha = 0.5f)
                )
                Spacer(Modifier.padding(top = 5.dp))
                Text(
                    text = "4 draws",
                    color = Color.DarkGray.copy(alpha = 0.5f),
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
@Preview
fun GameStatsPreview() {
    GameStats()
}

@Composable
fun GameBoard() {

    Surface(Modifier.padding(start = 15.dp, end = 15.dp,top = 25.dp)) {
        Column(Modifier.padding(start = 15.dp, end = 15.dp,top = 15.dp)) {
            Row() {
                Box(
                    Modifier
                        .weight(1f).aspectRatio(1f)
                        .border(1.dp, Color.LightGray.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "X", fontSize = 40.sp)
                }
                Box(
                    Modifier
                        .weight(1f).aspectRatio(1f)
                        .border(1.dp, Color.LightGray.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "X", fontSize = 40.sp)
                }
                Box(
                    Modifier
                        .weight(1f).aspectRatio(1f)
                        .border(1.dp, Color.LightGray.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "X", fontSize = 40.sp)
                }
            }
            Row() {
                Box(
                    Modifier
                        .weight(1f).aspectRatio(1f)
                        .border(1.dp, Color.LightGray.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "X", fontSize = 40.sp)
                }
                Box(
                    Modifier
                        .weight(1f).aspectRatio(1f)
                        .border(1.dp, Color.LightGray.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "X", fontSize = 40.sp)
                }
                Box(
                    Modifier
                        .weight(1f).aspectRatio(1f)
                        .border(1.dp, Color.LightGray.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "X", fontSize = 40.sp)
                }
            }
            Row() {
                Box(
                    Modifier
                        .weight(1f).aspectRatio(1f)
                        .border(1.dp, Color.LightGray.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "X", fontSize = 40.sp)
                }
                Box(
                    Modifier
                        .weight(1f).aspectRatio(1f)
                        .border(1.dp, Color.LightGray.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "X", fontSize = 40.sp)
                }
                Box(
                    Modifier
                        .weight(1f).aspectRatio(1f)
                        .border(1.dp, Color.LightGray.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "X", fontSize = 40.sp)
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GameBoardPreview() {
    GameBoard()
}