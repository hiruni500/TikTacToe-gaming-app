package com.example.tiktactoe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

object GameData {
    // MutableLiveData to hold the current game model
    private var _gameModel : MutableLiveData<GameModel> = MutableLiveData()
    // LiveData to expose the game model to observers

    var gameModel : LiveData<GameModel> = _gameModel
    // Variable to hold the user's ID
    var myID = ""

// Function to save the game model to Firestore.
    fun saveGameModel(model : GameModel){
        _gameModel.postValue(model)

    // If the game has an ID, update it in Firestore
        if(model.gameId!="-1"){
            Firebase.firestore.collection("games")
                .document(model.gameId)
                .set(model)
        }


    }

    //Function to fetch the game model from Firestore.
    fun fetchGameModel(){
        gameModel.value?.apply {
            if(gameId!="-1"){
                Firebase.firestore.collection("games")
                    .document(gameId)
                    .addSnapshotListener { value, error ->
                        val model = value?.toObject(GameModel::class.java)
                        // Update the game model in LiveData
                        _gameModel.postValue(model)
                    }
            }
        }
    }




}