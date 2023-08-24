package com.example.clicker.shop

import android.widget.Toast
import com.example.clicker.GameState
import java.lang.Exception

class Shop (override var gameState: GameState) : IShop{

    override var items: Map<ItemName, Item> = mutableMapOf(
        ItemName.MOUSE to Item(ItemName.MOUSE, 10, 0, 0),
        ItemName.KEYBOARD to Item(ItemName.KEYBOARD, 100, 0, 10),
        ItemName.PROGRAMMER to Item(ItemName.PROGRAMMER, 5_000, 0, 500),
        ItemName.FRIENDS to Item(ItemName.FRIENDS, 80_000, 0, 8000),
        ItemName.TEAM to Item(ItemName.TEAM, 1_000_000, 0, 10000),
        ItemName.COMPANY to Item(ItemName.COMPANY, 50_000_000, 0, 500000)
    )
    init {
        val boughtItems = gameState.items
        boughtItems.forEach { item ->
            items[item.key]!!.boughtTimes = item.value.boughtTimes
        }
    }

    override fun buy(itemName: ItemName): Boolean {
        val item: Item = items[itemName]
            ?: throw IllegalArgumentException("There is no such item in the shop")
        val itemPrice = item.getCurrentPrice()
        val currentBalance = gameState.pointsCount
        if (currentBalance >= itemPrice) {
            item.buy()
            if (gameState.items.containsKey(itemName)) {
                gameState.items[itemName]!!.boughtTimes++
            } else {
                gameState.items.put(itemName, item)
            }
            gameState.pointsCount -= itemPrice
            gameState.pointsPerSecond += item.pointsPerSecond
            if (itemName == ItemName.MOUSE) {
                gameState.pointsPerClick++
            }
            return true
        } else {
            println(gameState.toString())
            println(item.toString())
            println("You don't have enough points for this bought")
            return false
        }
    }

}