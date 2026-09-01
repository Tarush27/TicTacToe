package com.example.tictactoe

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
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
                            .padding(innerPadding), color = MaterialTheme.colorScheme.surface
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

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {

        Row(
            Modifier
                .padding(end = 10.dp, top = 20.dp, bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            GameStatsRail()
            GameBoardLandscape()
        }


    } else {
        Column(
            Modifier.padding(bottom = 10.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GameStats()
            GameBoard()
            PlayerType()
            Spacer(Modifier.padding(top = 40.dp))
            ResetGame()
        }
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
    Surface(Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.surface) {
        Row(Modifier.padding(top = 25.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painterResource(R.drawable.ic_circle),
                    contentDescription = "noughts",
                    Modifier.size(40.dp),
                    tint = Color(0xFF40BAD0)
                )
                Spacer(Modifier.padding(top = 5.dp))
                Text(
                    text = "4 wins",
                    color = Color(0xFF40BAD0),
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painterResource(R.drawable.ic_cross),
                    contentDescription = "cross",
                    Modifier.size(40.dp),
                    tint = Color(0xFF3E88CE)
                )
                Spacer(Modifier.padding(top = 5.dp))
                Text(
                    text = "4 wins",
                    color = Color(0xFF3E88CE),
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
                    tint = if(isSystemInDarkTheme())Color.Gray else Color.DarkGray.copy(alpha = 0.5f)
                )
                Spacer(Modifier.padding(top = 5.dp))
                Text(
                    text = "4 draws",
                    color = if(isSystemInDarkTheme())Color.Gray else Color.DarkGray.copy(alpha = 0.5f),
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


@Composable
fun GameBoard() {
    Surface(
        Modifier.padding(start = 35.dp, end = 35.dp, top = 40.dp),
        shape = RoundedCornerShape(18.dp),
        color = if (isSystemInDarkTheme()) Color(0xFF36343B) else Color(0xFFE3DCE8),
        border = if(isSystemInDarkTheme()) BorderStroke(1.dp, color = Color(0xFF36343B)) else BorderStroke(1.dp, color = Color(0xFFE3DCE8))
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.aspectRatio(1f),
            contentPadding = PaddingValues(20.dp),
            userScrollEnabled = false
        ) {
            items(9) { item ->
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(5.dp)
                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(20.dp))
                        .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(20.dp))
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(30.dp)
                        ), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "X",
                        fontSize = 30.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

            }
        }
    }

}

@Composable
fun PlayerType() {
    Surface(
        shape = RoundedCornerShape(50.dp),
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 35.dp), border = BorderStroke(width = 0.5.dp,color = MaterialTheme.colorScheme.outlineVariant)
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
fun GameStatsRail() {
    Surface() {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painterResource(R.drawable.ic_circle),
                        contentDescription = "noughts",
                        Modifier.size(40.dp),
                        tint = Color(0xFF40BAD0)
                    )
                    Text(
                        text = "4 wins",
                        color = Color(0xFF40BAD0),
                        fontSize = 20.sp,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painterResource(R.drawable.ic_cross),
                        contentDescription = "cross",
                        Modifier.size(40.dp),
                        tint = Color(0xFF3E88CE)
                    )
                    Text(
                        text = "4 wins",
                        color = Color(0xFF3E88CE),
                        fontSize = 20.sp,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(Modifier.padding(top = 15.dp))
            Icon(
                painterResource(R.drawable.ic_balance),
                contentDescription = "noughts",
                Modifier.size(40.dp),
                tint = if(isSystemInDarkTheme())Color.Gray else Color.DarkGray.copy(alpha = 0.5f)
            )
            Spacer(Modifier.padding(top = 5.dp))
            Text(
                text = "4 draws",
                color = if(isSystemInDarkTheme())Color.Gray else Color.DarkGray.copy(alpha = 0.5f),
                fontSize = 20.sp,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold
            )

            PlayerType()

            Spacer(Modifier.padding(top = 20.dp))
            ResetGameLandscape()
        }
    }
}


@Composable
fun ResetGameLandscape() {
    Button(
        {}, Modifier.width(300.dp)
    ) {
        Text(text = "Reset", fontSize = 20.sp)
    }
}

@Composable
fun GameBoardLandscape() {
    Surface(
        Modifier.padding(bottom = 20.dp, top = 15.dp, end = 20.dp),
        shape = RoundedCornerShape(18.dp),
        color = if (isSystemInDarkTheme()) Color(0xFF36343B) else Color(0xFFE3DCE8),
        border = if(isSystemInDarkTheme()) BorderStroke(1.dp, color = Color(0xFF36343B)) else BorderStroke(1.dp, color = Color(0xFFE3DCE8))
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.aspectRatio(1f),
            contentPadding = PaddingValues(20.dp),
            userScrollEnabled = false
        ) {
            items(9) { item ->
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(5.dp)
                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(20.dp))
                        .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(20.dp))
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(30.dp)
                        ), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "X",
                        fontSize = 30.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

            }
        }
    }

}