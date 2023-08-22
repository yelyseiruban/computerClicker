package com.example.clicker

class ClickAction(val gameState: GameState) {
    fun clickOnComputer() {
        gameState.pointsCount+=gameState.pointsPerClick
    }

    fun getPointsCount(): Long {
        return gameState.pointsCount
    }

}