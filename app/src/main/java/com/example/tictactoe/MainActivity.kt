package com.example.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
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

    Column(Modifier.padding(bottom = 10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        GameStats()
        GameBoard()
        PlayerType()
        Spacer(Modifier.padding(top = 40.dp))
        ResetGame()
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

    Surface(
        Modifier.padding(start = 35.dp, end = 35.dp, top = 40.dp),
        shape = RoundedCornerShape(18.dp),
        color = Color.LightGray,
        border = BorderStroke(1.dp, color = Color.LightGray)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(20.dp),
            userScrollEnabled = true
        ) {
            items(9) { item ->
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(5.dp)
                        .border(
                            1.dp,
                            Color.DarkGray.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(30.dp)
                        ), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "X",
                        fontSize = 30.sp,
                    )
                }

            }
        }
    }

}

@Composable
fun PlayerType() {
    Surface(
        shape = RoundedCornerShape(50.dp), color = Color(0xFFF1F3F4), // Light grey background
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 35.dp)
    ) {
        Row(
            modifier = Modifier.padding(4.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = "Player X",
                    tint = Color(0xFF3A86FF),
                    modifier = Modifier.size(28.dp)
                )
            }

            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF3A86FF)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Circle,
                    contentDescription = "Player O",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}


@Composable
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
fun PlayerTypePreview() {
    PlayerType()
}


@Composable
fun ResetGame() {
    Button(
        {}, Modifier
            .fillMaxWidth()
            .padding(start = 25.dp, end = 25.dp)
    ) {
        Text(text = "Reset", Modifier.padding(5.dp), fontSize = 20.sp)
    }
}

@Composable
@Preview
fun ResetGamePreview() {

    ResetGame()

}