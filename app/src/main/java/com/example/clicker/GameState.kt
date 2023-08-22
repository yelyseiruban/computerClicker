package com.example.clicker

data class GameState constructor(var pointsCount: Long,
                      var pointsPerClick: Int,
                      var pointsPerSecond: Int,
                      var items: Map<Item, Int>) {
    companion object {
        @Volatile
        private var instance: GameState? = null

        fun getInstance(pointsCount: Long,
                        pointsPerClick: Int,
                        pointsPerSecond: Int,
                        items: Map<Item, Int>): GameState {
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
}
