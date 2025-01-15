package com.example.xogame

import kotlin.random.Random

data class GameModel (
    var gameId : String = "-1",
    var gameMode: GameMode = GameMode.TWO_PLAYER,
    var filledPos : MutableList<String> = mutableListOf("","","","","","","","",""),
    var winner : String ="",
    var gameStatus : GameStatus = GameStatus.CREATED,
    var currentPlayer : String = (arrayOf("X","O"))[Random.nextInt(2)],
    var turnHistory: MutableList<Turn> = mutableListOf(),

)

enum class GameMode {
    SINGLE_PLAYER,
    TWO_PLAYER
}

data class Turn(
    val turnNumber: Int,        // ลำดับของตา
    val player: String,         // ผู้เล่นในตานั้น (X หรือ O)
    val position: Int           // ตำแหน่งที่เล่น (0-8)
)

enum class GameStatus{
    CREATED,
    JOINED,
    INPROGRESS,
    FINISHED
}
