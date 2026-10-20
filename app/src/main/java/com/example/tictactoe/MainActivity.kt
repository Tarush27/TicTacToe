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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
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
import com.example.tictactoe.ui.theme.AppTheme
import com.example.tictactoe.ui.theme.TicTacToeTheme
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.PropertyName
import com.google.firebase.database.database

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var selectedTheme by rememberSaveable { mutableStateOf(AppTheme.DEFAULT) }
            TicTacToeTheme(appTheme = selectedTheme) {
                Scaffold(
                    topBar = {
                        TopAppBar(title = {
                            Text(
                                "TicTacToe"
                            )
                        }, actions = {
                            MinimalDropdownMenu(onThemeSelected = { selectedTheme = it })
                        })
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
fun MinimalDropdownMenu(onThemeSelected: (AppTheme) -> Unit) {
    var expanded by rememberSaveable() { mutableStateOf(false) }
    Box(
        modifier = Modifier.padding(16.dp)
    ) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(Icons.Default.MoreVert, contentDescription = "More options")
        }
        DropdownMenu(
            expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(text = { Text("Default Theme") }, onClick = {
                onThemeSelected(AppTheme.DEFAULT)
                expanded = false
            })
            DropdownMenuItem(text = { Text("Red Theme") }, onClick = {
                onThemeSelected(AppTheme.RED)
                expanded = false
            })
            DropdownMenuItem(text = { Text("Yellow Theme") }, onClick = {
                onThemeSelected(AppTheme.YELLOW)
                expanded = false
            })
        }
    }
}

fun checkRowWinner(board: List<Player?>): Player? {
    if (board[0] != null && board[0] == board[1] && board[1] == board[2]) return board[0]
    if (board[3] != null && board[3] == board[4] && board[4] == board[5]) return board[3]
    if (board[6] != null && board[6] == board[7] && board[7] == board[8]) return board[6]
    return null
}

fun checkColumnWinner(board: List<Player?>): Player? {
    if (board[0] != null && board[0] == board[3] && board[3] == board[6]) return board[0]
    if (board[1] != null && board[1] == board[4] && board[4] == board[7]) return board[1]
    if (board[2] != null && board[2] == board[5] && board[5] == board[8]) return board[2]
    return null
}

fun checkDiagonalWinner(board: List<Player?>): Player? {
    if (board[0] != null && board[0] == board[4] && board[4] == board[8]) return board[0]
    if (board[2] != null && board[2] == board[4] && board[4] == board[6]) return board[2]
    return null
}

fun checkWinner(board: List<Player?>): Player? {
    return checkRowWinner(board) ?: checkColumnWinner(board) ?: checkDiagonalWinner(board)
}

@Composable
fun TicTacToeApp() {

    val databaseReference: DatabaseReference = Firebase.database.reference
    val context = LocalContext.current

    var ticTacToeUiState by rememberSaveable(stateSaver = TicTacToeUiState.Saver) {
        mutableStateOf(TicTacToeUiState())
    }

    fun updateGameStateToFirebase() {
//        val gameStat = GameStat(xWins, oWins, draws)
//        val gameState = GameState(
//            board.map { it?.name ?: "" }, currentPlayer = currentPlayer.name, isGameOver, gameStat
//        )

//        databaseReference.child("games").child("123").setValue(
//            gameState
//        ) { error, _ -> if (error == null) println("Success") else println("db ref error : $error") }
    }

//    DisposableEffect(Unit) {
//        val listener = object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val remoteGameState = snapshot.getValue(GameState::class.java)
//
//                Log.d("MainActivity_frd", "onDataChange:  $remoteGameState")
//
////                if (remoteGameState != null) {
////                    remoteGameState.board.forEachIndexed { index, string ->
////                        board[index] = when (string) {
////                            "X" -> Player.X
////                            "O" -> Player.O
////                            else -> null
////                        }
////                    }
////
////                    currentPlayer = if (remoteGameState.currentPlayer == "O") Player.O else Player.X
////                    isGameOver = remoteGameState.isGameOver
////                    xWins = remoteGameState.stats.x_wins
////                    oWins = remoteGameState.stats.o_wins
////                    draws = remoteGameState.stats.draws
////
////                }
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.d("MainActivity_frd", "databaseError: $error")
//            }
//
//        }
//        databaseReference.child("games").child("123").addValueEventListener(listener)
//
//        onDispose {
//            databaseReference.child("games").child("123").removeEventListener(listener)
//        }
//    }


    fun makeMove(position: Int) {

        if (ticTacToeUiState.board[position] != null || ticTacToeUiState.isGameOver()) return

        val newBoard = ticTacToeUiState.board.toMutableList().apply {
            this[position] = ticTacToeUiState.currentPlayer
        }
        val winner = checkWinner(newBoard)
        if (winner != null) {
            val updatedGameStats = if (winner == Player.X) {
                ticTacToeUiState.gameStat.copy(
                    x_wins = ticTacToeUiState.gameStat.x_wins + 1,
                )
            } else {
                ticTacToeUiState.gameStat.copy(
                    o_wins = ticTacToeUiState.gameStat.o_wins + 1,
                )
            }

            ticTacToeUiState = ticTacToeUiState.copy(
                board = newBoard, gameStat = updatedGameStats
            )
            println(newBoard.toString())
            println("Winner is: $winner")
            Toast.makeText(context, "Game over $winner wins", Toast.LENGTH_LONG).show()
            ticTacToeUiState.isGameOver()
//            updateGameStateToFirebase()
            return
        }

        if (newBoard.all { it != null }) {

            val updatedGameStats = ticTacToeUiState.gameStat.copy(
                draws = ticTacToeUiState.gameStat.draws + 1,
            )

            ticTacToeUiState = ticTacToeUiState.copy(
                board = newBoard, gameStat = updatedGameStats
            )
            println("its a Draw")
            Toast.makeText(context, "Game Over, Its a Draw", Toast.LENGTH_LONG).show()
            ticTacToeUiState.isGameOver()
//            updateGameStateToFirebase()
            return
        }

        val latestPlayer = if (ticTacToeUiState.currentPlayer == Player.X) Player.O else Player.X

        ticTacToeUiState = ticTacToeUiState.copy(
            board = newBoard, currentPlayer = latestPlayer
        )

//        updateGameStateToFirebase()

    }


    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {

        Row(
            Modifier.padding(end = 10.dp, bottom = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                GameStatsRail(
                    xWins = ticTacToeUiState.gameStat.x_wins,
                    oWins = ticTacToeUiState.gameStat.o_wins,
                    drawCount = ticTacToeUiState.gameStat.draws,
                    currentPlayer = ticTacToeUiState.currentPlayer
                )
                RestartGameLandscape {
                    ticTacToeUiState = TicTacToeUiState(gameStat = ticTacToeUiState.gameStat)
                    ticTacToeUiState.isGameOver()
//                    updateGameStateToFirebase()
                }
            }
            GameBoardLandscape(board = ticTacToeUiState.board, onCellClick = { position ->
                makeMove(position)
            })
        }


    } else {
        Column(
            Modifier.padding(bottom = 10.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GameStats(
                xWins = ticTacToeUiState.gameStat.x_wins,
                oWins = ticTacToeUiState.gameStat.o_wins,
                drawCount = ticTacToeUiState.gameStat.draws
            )
            GameBoard(board = ticTacToeUiState.board, onCellClick = { position ->
                makeMove(position)
            })
            PlayerType(currentPlayer = ticTacToeUiState.currentPlayer)
            Spacer(Modifier.padding(top = 40.dp))
            RestartGame {
                ticTacToeUiState = TicTacToeUiState(gameStat = ticTacToeUiState.gameStat)
                ticTacToeUiState.isGameOver()
//                updateGameStateToFirebase()
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
fun GameStats(xWins: Long, oWins: Long, drawCount: Long) {
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
fun GameStatsRail(xWins: Long, oWins: Long, drawCount: Long, currentPlayer: Player) {
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

data class GameState(
    val board: List<String> = List(9) { "" },
    val currentPlayer: String = "X",
    val isGameOver: Boolean = false,
    val stats: GameStat = GameStat(),
)

data class GameStat(
    @get:PropertyName("x_wins") val x_wins: Long = 0L,
    @get:PropertyName("o_wins") val o_wins: Long = 0L,
    @get:PropertyName("draws") val draws: Long = 0L
)

data class TicTacToeUiState(
    val board: List<Player?> = List(9) { null },
    val currentPlayer: Player = Player.X,
    val gameStat: GameStat = GameStat()
) {
    fun isGameOver(): Boolean = checkWinner(board) != null || board.all { it != null }

    companion object {
        val Saver: Saver<TicTacToeUiState, *> = listSaver(save = { state ->
            listOf(
                state.board.map { it?.name },
                state.currentPlayer.name,
                state.gameStat.x_wins,
                state.gameStat.o_wins,
                state.gameStat.draws
            )
        }, restore = { list ->
            @Suppress("UNCHECKED_CAST") val boardList = (list[0] as List<String?>).map { name ->
                name?.let { Player.valueOf(it) }
            }
            val currentPlayer = Player.valueOf(list[1] as String)
            val xWins = list[2] as Long
            val oWins = list[3] as Long
            val draws = list[4] as Long
            TicTacToeUiState(
                board = boardList,
                currentPlayer = currentPlayer,
                gameStat = GameStat(x_wins = xWins, o_wins = oWins, draws = draws)
            )
        })
    }
}