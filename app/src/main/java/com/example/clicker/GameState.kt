package com.example.clicker

import android.os.Handler
import com.example.clicker.shop.Item
import com.example.clicker.shop.ItemName

data class GameState constructor(var pointsCount: Long,
                      var pointsPerClick: Int,
                      var pointsPerSecond: Int,
                      var items: MutableMap<ItemName, Item>) : Observer {
    companion object {
        @Volatile
        private var instance: GameState? = null

        fun getInstance(pointsCount: Long,
                        pointsPerClick: Int,
                        pointsPerSecond: Int,
                        items: MutableMap<ItemName, Item>): GameState {
            return instance?: synchronized(this) {
                instance?: GameState(pointsCount, pointsPerClick, pointsPerSecond, items)
            }
        }

        fun getInstance(): GameState {
            return getInstance(0, 1, 0, mutableMapOf())
        }
    }
    fun updateInstance(gameState: GameState) {
        this.pointsCount = gameState.pointsCount
        this.pointsPerClick = gameState.pointsPerClick
        this.pointsPerSecond = gameState.pointsPerSecond
        this.items = gameState.items
    }

    @Transient
    override val listeners: MutableList<(GameState) -> Unit> = mutableListOf<(GameState) -> Unit>()

    override fun addObserver(listener: (GameState) -> Unit) {
        listeners.add(listener)
    }

    override fun removeObserver(listener: (GameState) -> Unit) {
        listeners.remove(listener)
    }

    override fun notifyObservers() {
        listeners.forEach { it.invoke(this) }
    }

    override fun updateState(newState: GameState) {
        updateInstance(newState)
        notifyObservers()
    }

    override fun updatePointsCount(pointsCount: Long) {
        this.pointsCount = pointsCount
        notifyObservers()
    }

    override fun updatePointsPerSecond(pointsPerSecond: Int) {
        this.pointsPerSecond = pointsPerSecond
        notifyObservers()
    }

    @Transient
    private val handler = Handler()

    @Transient
    private val updateInterval = 1000L // 1 second

    private val updateRunnable = object : Runnable {
        override fun run() {
            pointsCount += pointsPerSecond
            notifyObservers()
            handler.postDelayed(this, updateInterval)
        }
    }

    init {
        handler.postDelayed(updateRunnable, updateInterval)
    }

    fun stopUpdating() {
        handler.removeCallbacks(updateRunnable)
    }
}
