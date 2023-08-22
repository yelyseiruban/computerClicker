package com.example.clicker

interface IStateAct {
    var state: GameState
    fun save()
    fun load(): GameState
    fun update(gameState: GameState)
}