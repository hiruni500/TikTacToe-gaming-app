package com.example.tiktactoe

import kotlin.random.Random

data class GameModel(
    var gameId : String = "-1",
    var filledPos : MutableList<String> = mutableListOf("","","","","","","","",""),
    var winner : String ="",
    var gameStatus : GameStatus = GameStatus.CREATED,
    var currentPlayer : String = (arrayOf("X","O"))[Random.nextInt(2)]


)
enum class GameStatus{
    CREATED,   // Game created but not joined by another player yet
    JOINED,        // Game joined by another player, waiting to start
    INPROGRESS,
    FINISHED
}