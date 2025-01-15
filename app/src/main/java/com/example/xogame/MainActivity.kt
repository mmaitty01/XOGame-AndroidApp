package com.example.xogame

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.xogame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btn.setOnClickListener {
            createSinglePlayerGame()
        }

        binding.btn2.setOnClickListener {
            createTwoPlayerGame()
        }

    }

    fun createSinglePlayerGame() {

        //Single Player
        GameData.saveGameModel(
            GameModel(
                gameStatus = GameStatus.JOINED,
                gameMode = GameMode.SINGLE_PLAYER
            )
        )
        startGame()
    }

    fun createTwoPlayerGame() {
      //Two Player
        GameData.saveGameModel(
            GameModel(
                gameStatus = GameStatus.JOINED,
                gameMode = GameMode.TWO_PLAYER
            )
        )
        startGame()
    }

    fun startGame() {
        startActivity(Intent(this,GameActivity::class.java))
    }
}
