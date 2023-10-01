package com.example.clicker.model.game

import com.example.clicker.model.Repository
import com.example.clicker.views.base.PointsObserver

interface GameRepository : Repository {

    var currentPoints: Long
    var boughtItems: MutableMap<ItemName, ShopItemInfo>

    fun getPointsPerClick() : Int

    fun getPointsPerSecond(): Int

    fun increasePoints(pointsCount: Long)

    fun decreasePoints(pointsCount: Long)

    fun buyItem(itemName: ItemName)

    fun getCurrentItemPrice(itemName: ItemName) : Long

    fun addObserver(observer: PointsObserver)

    fun removeObserver(observer: PointsObserver)
}