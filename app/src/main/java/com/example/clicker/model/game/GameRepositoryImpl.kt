package com.example.clicker.model.game

import android.os.Handler
import android.os.Looper
import com.example.clicker.GameStatePreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.clicker.exceptions.NoSuchItemException
import com.example.clicker.exceptions.NotEnoughPointsException
import com.example.clicker.views.base.PointsObserver
import java.lang.Exception
import kotlin.math.ceil
import kotlin.math.pow
import kotlin.properties.Delegates

class GameRepositoryImpl(
    gameStatePreferences: GameStatePreferences
) : GameRepository {

    override var currentPoints by Delegates.notNull<Long>()
    override lateinit var boughtItems: MutableMap<ItemName, ShopItemInfo>

    private val observers = mutableListOf<PointsObserver>()

    private val handler = Handler(Looper.getMainLooper())

    init {
        gameStatePreferences.upload()
        currentPoints = gameStatePreferences.gameState.currentPoints
        boughtItems = gameStatePreferences.gameState.boughtItems

        val updateIntervalMillis = 1000L
        val updateTask = object : Runnable {
            override fun run() {
                increasePoints(getPointsPerSecond().toLong())
                handler.postDelayed(this, updateIntervalMillis)
            }
        }
        handler.postDelayed(updateTask, updateIntervalMillis)
    }

    fun stopUpdatingPoints() {
        handler.removeCallbacksAndMessages(null)
    }

    override fun increasePoints(pointsCount: Long) {
        currentPoints += pointsCount
        notifyObservers(currentPoints)
    }

    override fun decreasePoints(pointsCount: Long) {
        currentPoints -= pointsCount
        notifyObservers(currentPoints)
    }

    override fun addObserver(observer: PointsObserver) {
        observers.add(observer)
    }

    override fun removeObserver(observer: PointsObserver) {
        observers.remove(observer)
    }

    private fun notifyObservers(newPoints: Long) {
        for (observer in observers) {
            observer.onPointsChanged(newPoints)
        }
    }

    override fun buyItem(itemName: ItemName) {
        val itemPrice = getCurrentItemPrice(itemName)
        if (currentPoints < itemPrice) throw NotEnoughPointsException()
        val prevShopItemInfo: ShopItemInfo =
            boughtItems[itemName] ?: throw NoSuchItemException()
        prevShopItemInfo.boughtTimes += 1
        boughtItems[itemName] = prevShopItemInfo
        decreasePoints(itemPrice)
    }


    override fun getPointsPerClick() : Int {
        return boughtItems[ItemName.MOUSE]!!.boughtTimes + 1
    }

    override fun getPointsPerSecond(): Int {
        var pointsPerSecond = 0
        boughtItems.forEach { item ->
            val shopItemInfo: ShopItemInfo = item.value
            pointsPerSecond += shopItemInfo.boughtTimes * shopItemInfo.itemCharacteristics.pointsPerSecond
        }

        return pointsPerSecond
    }


    override fun getCurrentItemPrice(itemName: ItemName): Long {
        val shopItemInfo: ShopItemInfo =
            boughtItems[itemName] ?: throw Exception("There is no such item")
        val startPrice = shopItemInfo.itemCharacteristics.startPrice
        val boughtTimes = boughtItems[itemName]!!.boughtTimes
        //Complex percent
        return ceil(startPrice * 1.1.pow(boughtTimes.toDouble())).toLong()
    }
}

data class ShopItemInfo (
    val image: Int,
    val itemCharacteristics: ItemCharacteristics,
    var boughtTimes: Int,
    var itemName: ItemName
)