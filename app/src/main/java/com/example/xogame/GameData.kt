package com.example.xogame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object GameData {
    private var _gameModel : MutableLiveData<GameModel> = MutableLiveData()
    var gameModel : LiveData<GameModel> = _gameModel


    fun saveGameModel(model : GameModel){
        _gameModel.postValue(model)
        //เก็บข้อมูลลง Firebase
        Firebase.firestore.collection("games")
            .document(model.gameId)
            .set(model)


    }

}