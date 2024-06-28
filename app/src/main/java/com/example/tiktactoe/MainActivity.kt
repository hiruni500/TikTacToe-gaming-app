package com.example.tiktactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import com.example.tiktactoe.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlin.random.Random
import kotlin.random.nextInt


class MainActivity() : AppCompatActivity() {

    lateinit var binding :ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.playOfflineBtn.setOnClickListener{
            createOfflineGame()
        }

        binding.createOnlineGameBtn.setOnClickListener {
               createOnlineGame()
        }

        binding.joinOnlineGameBtn.setOnClickListener{
            joinOnlineGame()
        }
    }

    // Function to create an offline game
    fun createOfflineGame(){
        GameData.saveGameModel(
            GameModel(
                gameStatus = GameStatus.JOINED
            )
        )
        startGame()
    }

    // Function to create an online game
    fun createOnlineGame(){
        GameData.myID = "X"
        GameData.saveGameModel(
            GameModel(
                gameStatus = GameStatus.CREATED,
                gameId = Random.nextInt(1000..9999).toString()
            )
        )
        startGame()
    }


    // Function to join an online game

    fun joinOnlineGame(){
        var gameId=binding.gameIdInput.text.toString()
        if(gameId.isEmpty()){
            binding.gameIdInput.setError("Please enter game ID")
            return
        }
        GameData.myID = "O"
        Firebase.firestore.collection("games")
            .document(gameId)
            .get()
            .addOnSuccessListener {
                val model = it ?.toObject(GameModel::class.java)
                if(model==null){
                    binding.gameIdInput.setError("Please enter valid game ID")
                }else{
                    model.gameStatus = GameStatus.JOINED
                    GameData.saveGameModel(model)
                    startGame()
                }
            }

    }





    fun startGame(){
        startActivity(Intent(this,GameActivity::class.java))
    }


}