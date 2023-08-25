package com.example.clicker

class ClickAction(val gameState: GameState) {
    fun clickOnComputer() {
        val newPointsCount = gameState.pointsCount + gameState.pointsPerClick
        gameState.updatePointsCount(newPointsCount)
    }

    fun getPointsCount(): Long {
        return gameState.pointsCount
    }

}