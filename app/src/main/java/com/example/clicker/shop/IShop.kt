package com.example.clicker.shop

import com.example.clicker.GameState

interface IShop {
    var gameState: GameState

    var items: Map<ItemName, Item>
    fun buy(itemName: ItemName): Boolean
}