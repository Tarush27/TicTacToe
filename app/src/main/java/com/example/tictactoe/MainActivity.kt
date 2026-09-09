package com.example.tictactoe

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tictactoe.ui.theme.TicTacToeTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TicTacToeTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(title = {
                            Text(
                                "TicTacToe"
                            )
                        }, actions = {
                            MinimalDropdownMenu()
                        }
                        )
                    }


                ) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        color = MaterialTheme.colorScheme.surface
                    ) {
                        TicTacToeApp()
                    }

                }
            }
        }
    }
}

@Composable
fun MinimalDropdownMenu() {
    var expanded by rememberSaveable() { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .padding(16.dp)
    ) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(Icons.Default.MoreVert, contentDescription = "More options")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Colors") },
                onClick = { }
            )
        }
    }
}

@Composable
fun TicTacToeApp() {

    var currentPlayer by rememberSaveable { mutableStateOf(Player.X) }
    val board = rememberSaveable {
        mutableStateListOf<Player?>(
            null, null, null, null, null, null, null, null, null
        )
    }
    val context = LocalContext.current
    var xWins by rememberSaveable { mutableStateOf(0) }
    var oWins by rememberSaveable { mutableStateOf(0) }
    var draws by rememberSaveable { mutableStateOf(0) }
    var isGameOver by rememberSaveable { mutableStateOf(false) }

    fun getWinningDiagonalPlayer(): Player? {
        if (board[0] != null && board[0] == board[4] && board[4] == board[8]) {
            return board[0]
        }

        if (board[2] != null && board[2] == board[4] && board[4] == board[6]) {
            return board[2]
        }
        return null
    }

    fun getWinningColumnPlayer(): Player? {
        if (board[0] != null && board[0] == board[3] && board[3] == board[6]) {
            return board[0]
        }
        if (board[1] != null && board[1] == board[4] && board[4] == board[7]) {
            return board[1]
        }

        if (board[2] != null && board[2] == board[5] && board[5] == board[8]) {
            return board[2]
        }
        return null
    }

    fun getWinningRowPlayer(): Player? {
        if (board[0] != null && board[0] == board[1] && board[1] == board[2]) {
            return board[0]
        }

        if (board[3] != null && board[3] == board[4] && board[4] == board[5]) {
            return board[3]
        }
        if (board[6] != null && board[6] == board[7] && board[7] == board[8]) {
            return board[6]
        }
        return null
    }

    fun checkWinner(): Player? {
        val rowWinner = getWinningRowPlayer()
        if (rowWinner != null) {
            return rowWinner
        }
        val columnWinner = getWinningColumnPlayer()
        if (columnWinner != null) {
            return columnWinner
        }

        val diagonalWinner = getWinningDiagonalPlayer()
        if (diagonalWinner != null) {
            return diagonalWinner
        }
        return null
    }

    fun makeMove(position: Int) {

        if (board[position] != null) {
            return
        }

        if (isGameOver) return
        board[position] = currentPlayer

        val winner = checkWinner()
        if (winner != null) {
            if (winner == Player.X) xWins++ else oWins++
            println(board.toString())
            println("Winner is: $winner")
            isGameOver = true
            Toast.makeText(context, "Game over $winner wins", Toast.LENGTH_LONG).show()
            return
        }

        if (board.all { it != null }) {
            draws++
            isGameOver = true
            println("its a Draw")
            Toast.makeText(context, "Game Over, Its a Draw", Toast.LENGTH_LONG).show()
            return
        }
        currentPlayer = when (currentPlayer) {
            Player.X -> Player.O
            Player.O -> Player.X
        }


    }


    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {

        Row(
            Modifier.padding(end = 10.dp, top = 20.dp, bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                GameStatsRail(xWins, oWins, draws, currentPlayer)
                RestartGameLandscape {
                    currentPlayer = Player.X
                    board.forEachIndexed { index, player -> board[index] = null }
                    isGameOver = false
                }
            }
            GameBoardLandscape(board = board, onCellClick = { position ->
                makeMove(position)
            })
        }


    } else {
        Column(
            Modifier.padding(bottom = 10.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GameStats(xWins, oWins, draws)
            GameBoard(board = board, onCellClick = { position ->
                makeMove(position)
            })
            PlayerType(currentPlayer)
            Spacer(Modifier.padding(top = 40.dp))
            RestartGame {
                currentPlayer = Player.X
                board.forEachIndexed { index, player ->
                    board[index] = null
                }
                isGameOver = false
            }
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
fun GameStats(xWins: Int, oWins: Int, drawCount: Int) {
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
                    text = if (oWins > 0) "$oWins wins" else "$oWins win",
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
                    text = if (xWins > 0) "$xWins wins" else "$xWins win",
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
                    tint = if (isSystemInDarkTheme()) Color.Gray else Color.DarkGray.copy(alpha = 0.5f)
                )
                Spacer(Modifier.padding(top = 5.dp))
                Text(
                    text = "$drawCount draw",
                    color = if (isSystemInDarkTheme()) Color.Gray else Color.DarkGray.copy(alpha = 0.5f),
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


@Composable
fun GameBoard(board: List<Player?>, onCellClick: (Int) -> Unit) {
    Surface(
        Modifier.padding(start = 35.dp, end = 35.dp, top = 40.dp),
        shape = RoundedCornerShape(18.dp),
        color = if (isSystemInDarkTheme()) Color(0xFF36343B) else Color(0xFFE3DCE8),
        border = if (isSystemInDarkTheme()) BorderStroke(
            1.dp, color = Color(0xFF36343B)
        ) else BorderStroke(1.dp, color = Color(0xFFE3DCE8))
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.aspectRatio(1f),
            contentPadding = PaddingValues(20.dp),
            userScrollEnabled = false
        ) {
            items(9) { pos ->
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(5.dp)
                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(20.dp))
                        .background(
                            MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(20.dp)
                        )
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(30.dp)
                        )
                        .clickable { onCellClick(pos) }, contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = board[pos]?.name ?: "",
                        fontSize = 30.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

            }
        }
    }

}

@Composable
fun PlayerType(currentPlayer: Player) {
    Surface(
        shape = RoundedCornerShape(50.dp),
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 35.dp),
        border = BorderStroke(width = 0.5.dp, color = MaterialTheme.colorScheme.outlineVariant)
    ) {
        Row(
            modifier = Modifier.padding(4.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(if (currentPlayer == Player.X) Color(0xFF3A86FF) else Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = "Player X",
                    tint = if (currentPlayer == Player.X) Color.White else Color(0xFF3A86FF),
                    modifier = Modifier.size(28.dp)
                )
            }

            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(if (currentPlayer == Player.O) Color(0xFF3A86FF) else Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Circle,
                    contentDescription = "Player O",
                    tint = if (currentPlayer == Player.O) Color.White else Color(0xFF3A86FF),
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}


@Composable
fun RestartGame(onReset: () -> Unit) {
    Button(
        {
            onReset()
        }, Modifier
            .fillMaxWidth()
            .padding(start = 25.dp, end = 25.dp)
    ) {
        Text(text = "Restart", Modifier.padding(5.dp), fontSize = 20.sp)
    }
}


@Composable
fun GameStatsRail(xWins: Int, oWins: Int, drawCount: Int, currentPlayer: Player) {
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
                        text = if (oWins > 0) "$oWins wins" else "$oWins win",
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
                        text = if (xWins > 0) "$xWins wins" else "$xWins win",
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
                contentDescription = "balance",
                Modifier.size(40.dp),
                tint = if (isSystemInDarkTheme()) Color.Gray else Color.DarkGray.copy(alpha = 0.5f)
            )
            Spacer(Modifier.padding(top = 5.dp))
            Text(
                text = "$drawCount draw",
                color = if (isSystemInDarkTheme()) Color.Gray else Color.DarkGray.copy(alpha = 0.5f),
                fontSize = 20.sp,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold
            )

            PlayerType(currentPlayer)

            Spacer(Modifier.padding(top = 20.dp))
        }
    }
}


@Composable
fun RestartGameLandscape(onReset: () -> Unit) {
    Button(
        {
            onReset()
        }, Modifier.width(300.dp)
    ) {
        Text(text = "Restart", fontSize = 20.sp)
    }
}

@Composable
fun GameBoardLandscape(board: List<Player?>, onCellClick: (Int) -> Unit) {
    Surface(
        Modifier.padding(bottom = 20.dp, top = 15.dp, end = 20.dp),
        shape = RoundedCornerShape(18.dp),
        color = if (isSystemInDarkTheme()) Color(0xFF36343B) else Color(0xFFE3DCE8),
        border = if (isSystemInDarkTheme()) BorderStroke(
            1.dp, color = Color(0xFF36343B)
        ) else BorderStroke(1.dp, color = Color(0xFFE3DCE8))
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.aspectRatio(1f),
            contentPadding = PaddingValues(20.dp),
            userScrollEnabled = false
        ) {
            items(9) { pos ->
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(5.dp)
                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(20.dp))
                        .background(
                            MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(20.dp)
                        )
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(30.dp)
                        )
                        .clickable { onCellClick(pos) }, contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = board[pos]?.name ?: "",
                        fontSize = 30.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

            }
        }
    }

}

enum class Player {
    X, O
}