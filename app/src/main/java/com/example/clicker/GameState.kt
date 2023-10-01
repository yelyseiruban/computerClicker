package com.example.clicker

import android.content.Context
import android.content.SharedPreferences
import com.example.clicker.model.game.ItemCharacteristics
import com.example.clicker.model.game.ItemName
import com.example.clicker.model.game.ShopItemInfo
import com.google.gson.Gson

class GameState (
    val boughtItems: MutableMap<ItemName, ShopItemInfo>,
    val currentPoints: Long
)

const val APP_PREF = "APP_PREF"
const val CURRENT_POINTS = "CURRENT_POINTS"
const val GAME_STATE = "GAME_STATE"
class GameStatePreferences (context: Context) {

    lateinit var gameState: GameState
    private val preferences: SharedPreferences = context.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE)

    fun upload() {
        if (!preferences.contains(CURRENT_POINTS) || !preferences.contains(GAME_STATE)){
            val initialItemsAssortment = mutableMapOf<ItemName, ShopItemInfo>(
                ItemName.MOUSE to ShopItemInfo(R.drawable.mouse_icon_item, ItemCharacteristics(10, 0), 0, ItemName.MOUSE),
                ItemName.KEYBOARD to ShopItemInfo(R.drawable.keyboard_icon_item, ItemCharacteristics(100, 10), 0, ItemName.KEYBOARD),
                ItemName.PROGRAMMER to ShopItemInfo(R.drawable.programmer_icon_item, ItemCharacteristics(5_000, 500), 0, ItemName.PROGRAMMER),
                ItemName.FRIENDS to ShopItemInfo(R.drawable.friends_icon_item, ItemCharacteristics(80_000, 8_000), 0, ItemName.FRIENDS),
                ItemName.TEAM to ShopItemInfo(R.drawable.team_icon_item, ItemCharacteristics(1_000_000, 100_000), 0, ItemName.TEAM),
                ItemName.COMPANY to ShopItemInfo(R.drawable.company_icon_item, ItemCharacteristics(50_000_000, 500_000), 0, ItemName.COMPANY)
            )
            val gameState = GameState(initialItemsAssortment, 0)
            val initialGameStateJson = Gson().toJson(gameState)
            preferences.edit()
                .putString(GAME_STATE, initialGameStateJson)
                .apply()
        }
        val gameStateJson = preferences.getString(GAME_STATE, "null")
        val gameState = Gson().fromJson(gameStateJson, GameState::class.java)

        this.gameState = gameState
    }

    fun save(gameState: GameState) {
        val gameStateJson = Gson().toJson(gameState)

        preferences.edit()
            .putString(GAME_STATE, gameStateJson)
            .apply()
    }
}