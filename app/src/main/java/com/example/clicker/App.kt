package com.example.clicker

import android.app.Application
import com.example.clicker.model.game.GameRepository
import com.example.clicker.model.game.GameRepositoryImpl

class App : Application() {
    var models: MutableList<GameRepository> = mutableListOf()

    override fun onCreate() {
        super.onCreate()
        val gameRepository = GameRepositoryImpl(GameStatePreferences(applicationContext))
        models.add(gameRepository)
    }

    fun saveGameState() {
        val repository = this.models[0]
        val gameState = GameState(repository.boughtItems, repository.currentPoints)
        GameStatePreferences(applicationContext).save(gameState)
    }

}