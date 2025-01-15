package com.example.xogame

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.xogame.databinding.ActivityGameBinding
import kotlin.random.Random
import kotlin.random.nextInt


class GameActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityGameBinding

    private var gameModel : GameModel? = null

    private val handler = Handler(Looper.getMainLooper()) // สำหรับการหน่วงเวลาใน Replay

    private lateinit var gridBoard: GridLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gridBoard = binding.gridBoard

        binding.btn0.setOnClickListener(this)
        binding.btn1.setOnClickListener(this)
        binding.btn2.setOnClickListener(this)
        binding.btn3.setOnClickListener(this)
        binding.btn4.setOnClickListener(this)
        binding.btn5.setOnClickListener(this)
        binding.btn6.setOnClickListener(this)
        binding.btn7.setOnClickListener(this)
        binding.btn8.setOnClickListener(this)

        binding.startGameBtn.setOnClickListener {
            startGame()
        }
        binding.reGameBtn.setOnClickListener {
            rePlay()
        }

        GameData.gameModel.observe(this){
            gameModel = it
            setUI()
        }

    }


    fun setUI(){
        gameModel?.apply {
            binding.btn0.text = filledPos[0]
            binding.btn1.text = filledPos[1]
            binding.btn2.text = filledPos[2]
            binding.btn3.text = filledPos[3]
            binding.btn4.text = filledPos[4]
            binding.btn5.text = filledPos[5]
            binding.btn6.text = filledPos[6]
            binding.btn7.text = filledPos[7]
            binding.btn8.text = filledPos[8]

            binding.startGameBtn.visibility = View.VISIBLE

            binding.reGameBtn.visibility =  View.INVISIBLE

            binding.gameStatusText.text =
                when(gameStatus){
                    GameStatus.CREATED -> {
                        binding.startGameBtn.visibility = View.INVISIBLE
                        "Game ID :"+ gameId
                    }
                    GameStatus.JOINED ->{ //ก่อนเริ่มเกม
                        "Click on start game"
                    }
                    GameStatus.INPROGRESS ->{ //ระหว่างเล่นเกมแต่ละตา
                        binding.startGameBtn.visibility = View.INVISIBLE
                        currentPlayer + " turn"
                    }
                    GameStatus.FINISHED ->{ //หลังจบเกม
                        binding.reGameBtn.visibility =  View.VISIBLE
                        if(winner.isNotEmpty()) winner + " Won"
                        else "DRAW"

                    }
                }
        }
    }

//ฟังก์ชันเริ่มเกม
    fun startGame(){
        gameModel?.apply {
            updateGameData(
                GameModel(
                    gameId = gameId,
                    gameStatus = GameStatus.INPROGRESS,
                    gameMode = gameMode
                )
            )
        }
    }

//ฟังก์ชันเก็บdataลงฐานข้อมูล
    fun updateGameData(model : GameModel){
        GameData.saveGameModel(model)
    }

//ฟังก์ชันเช็คกระดานว่ามีผู้ชนะหรือไม่
    fun checkForWinner(){
        val winningPos = arrayOf(
            intArrayOf(0,1,2),
            intArrayOf(3,4,5),
            intArrayOf(6,7,8),
            intArrayOf(0,3,6),
            intArrayOf(1,4,7),
            intArrayOf(2,5,8),
            intArrayOf(0,4,8),
            intArrayOf(2,4,6),
        )

        gameModel?.apply {
            for ( i in winningPos){
                //012
                if(
                    filledPos[i[0]] == filledPos[i[1]] &&
                    filledPos[i[1]]== filledPos[i[2]] &&
                    filledPos[i[0]].isNotEmpty()
                ){
                    gameStatus = GameStatus.FINISHED
                    winner = filledPos[i[0]]
                }
            }
            if( filledPos.none(){ it.isEmpty() }){
                gameStatus = GameStatus.FINISHED
            }
           updateGameData(this)
        }
    }

    //ฟังก์ชันแสดงการเล่นซ้ำของเกมที่จบไป
    fun rePlay() {

        clearBoard() // ล้างกระดานก่อนเริ่มต้น

        gameModel?.apply {
            turnHistory.forEachIndexed { index, turn ->
                handler.postDelayed({
                    updateBoard(turn) // อัปเดตกระดานตามแต่ละ Turn
                    println("Replay Turn ${turn.turnNumber}: Player ${turn.player} at Position ${turn.position}")
                }, (index * 500).toLong()) // หน่วงเวลา 0.5 วินาทีระหว่างแต่ละ Turn
            }
        }
    }

    //ล้างกระดานให้เหมือนตอนเริ่มเกม
    private fun clearBoard() {
        for (i in 0 until gridBoard.childCount) {
            val button = gridBoard.getChildAt(i) as Button
            button.text = "" // ล้างข้อความในปุ่ม
        }
    }

    // ฟังก์ชันอัปเดตกระดานตาม Turn
    @RequiresApi(Build.VERSION_CODES.M)
    private fun updateBoard(turn: Turn) {
        val button = gridBoard.getChildAt(turn.position) as Button
        button.text = turn.player // ตั้งค่าข้อความให้เป็น "X" หรือ "O"
        setPlayerTextColor(button,turn.player)

    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(v: View?) {

        gameModel?.apply {
            if(gameStatus!= GameStatus.INPROGRESS){
                Toast.makeText(applicationContext,"Game not started",Toast.LENGTH_SHORT).show()
                return
            }
            //game is in progress
            val clickedPos =(v?.tag  as String).toInt()
            if(filledPos[clickedPos].isEmpty()) {
                filledPos[clickedPos] = currentPlayer

                val button = gridBoard.getChildAt(clickedPos) as Button
                setPlayerTextColor(button,currentPlayer) //แยกสีตัว X และ O

                if(gameMode == GameMode.TWO_PLAYER){ //สำหรับผู้เล่น 2 คน
                    val turn = Turn(turnNumber = turnHistory.size + 1, player = currentPlayer, position = clickedPos)
                    turnHistory.add(turn)
                    currentPlayer = if (currentPlayer == "X") "O" else "X"
                    checkForWinner()
                    updateGameData(this)


                }else{ //สำหรับผู้เล่น 1 คน (เล่นกับAi)
                    val turn = Turn(turnNumber = turnHistory.size + 1, player = currentPlayer, position = clickedPos)
                    turnHistory.add(turn)
                    currentPlayer = if (currentPlayer == "X") "O" else "X"
                    checkForWinner()
                    updateGameData(this)
                    if (filledPos.contains("") && gameStatus != GameStatus.FINISHED){
                        aiPlayer()
                    }

                }
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun aiPlayer() {
        //ตั้งค่าผู้เล่นAi
        gameModel?.apply {
            var clickedAi : Int
            do{
                clickedAi = Random.nextInt(0..8)
            }while (filledPos[clickedAi].isNotEmpty())

            filledPos[clickedAi]= currentPlayer

            val button = gridBoard.getChildAt(clickedAi) as Button
            setPlayerTextColor(button,currentPlayer)

            val turn = Turn(turnNumber = turnHistory.size + 1, player = currentPlayer, position = clickedAi)
            turnHistory.add(turn)
            currentPlayer = if (currentPlayer == "X") "O" else "X"
            checkForWinner()
            updateGameData(this)


        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun setPlayerTextColor(button: Button, player: String) {
        if (player == "X") {
            button.setTextColor(getColor(R.color.red)) // ใช้สีแดงสำหรับ X
        } else if (player == "O") {
            button.setTextColor(getColor(R.color.blue)) // ใช้สีน้ำเงินสำหรับ O
        }

    }

}