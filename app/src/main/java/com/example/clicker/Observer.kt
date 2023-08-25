package com.example.clicker

interface Observer {
    val listeners: MutableList<(GameState) -> Unit>
    fun addObserver(listener: (GameState) -> Unit)

    fun removeObserver(listener: (GameState) -> Unit)

    fun notifyObservers()

    fun updateState(newState: GameState)

    fun updatePointsCount(pointsCount: Long)

    fun updatePointsPerSecond(pointsPerSecond: Int)


}
