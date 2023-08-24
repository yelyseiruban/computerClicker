package com.example.clicker.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.clicker.GameState
import com.example.clicker.R
import com.example.clicker.StateAct
import com.example.clicker.formatPrice
import com.example.clicker.shop.Item
import com.example.clicker.shop.ItemName
import com.example.clicker.shop.Shop

class ShopActivity : AppCompatActivity() {
    private lateinit var stateAct: StateAct
    private lateinit var gameState: GameState
    private lateinit var shop: Shop
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)
        stateAct = StateAct(this)
        gameState = stateAct.load()
        shop = Shop(gameState)

        initShop()

        val gameIntent = Intent(this, GameActivity::class.java)
        val startGameActivity = {
            stateAct.save()
            startActivity(gameIntent)
        }
        val btnBack: Button = findViewById(R.id.btnBack)
        btnBack.setOnClickListener {
            startGameActivity()
        }
    }

    private fun initShop(){
        println("initShop")
        val tvMouseCount: TextView = findViewById(R.id.tvMouseCount)
        val tvMousePrice: TextView = findViewById(R.id.tvMousePrice)
        val ivMouse: ImageView = findViewById(R.id.ivMouse)
        val tvKeyboardCount: TextView = findViewById(R.id.tvKeyboardCount)
        val tvKeyboardPrice: TextView = findViewById(R.id.tvKeyboardPrice)
        val ivKeyboard: ImageView = findViewById(R.id.ivKeyboard)
        val tvProgrammerCount: TextView = findViewById(R.id.tvProgrammerCount)
        val tvProgrammerPrice: TextView = findViewById(R.id.tvProgrammerPrice)
        val ivProgrammer: ImageView = findViewById(R.id.ivProgrammer)
        val tvFriendsCount: TextView = findViewById(R.id.tvFriendsCount)
        val tvFriendsPrice: TextView = findViewById(R.id.tvFriendsPrice)
        val ivFriends: ImageView = findViewById(R.id.ivFriends)
        val tvTeamCount: TextView = findViewById(R.id.tvTeamCount)
        val tvTeamPrice: TextView = findViewById(R.id.tvTeamPrice)
        val ivTeam: ImageView = findViewById(R.id.ivTeam)
        val tvCompanyCount: TextView = findViewById(R.id.tvCompanyCount)
        val tvCompanyPrice: TextView = findViewById(R.id.tvCompanyPrice)
        val ivCompany: ImageView = findViewById(R.id.ivCompany)
        val setInitItemValues = { tvCount: TextView,  tvPrice: TextView, item: Item ->
            println("something")
            println(item.itemName)
            println(item.boughtTimes)
            tvCount.text = item.boughtTimes.toString()
            tvPrice.text = item.getCurrentPrice().toString()

        }
//        shop.items.forEach { item ->
//            run {
//                println(item.key)
//                println(item.value.boughtTimes)
//            }
//        }
        shop.items.forEach { item ->
            when (item.key) {
                ItemName.MOUSE -> {
                    println("MOUSE")
                    setInitItemValues(tvMouseCount, tvMousePrice, item.value)
                    ivMouse.setOnClickListener { buyItem(item.key, tvMouseCount, tvMousePrice, ivMouse) }
                }
                ItemName.KEYBOARD -> {
                    setInitItemValues(tvKeyboardCount, tvKeyboardPrice, item.value)
                    ivKeyboard.setOnClickListener { buyItem(item.key, tvKeyboardCount, tvKeyboardPrice, ivKeyboard) }
                }
                ItemName.PROGRAMMER -> {
                    setInitItemValues(tvProgrammerCount, tvProgrammerPrice, item.value)
                    ivProgrammer.setOnClickListener { buyItem(item.key, tvProgrammerCount, tvProgrammerPrice, ivProgrammer) }
                }
                ItemName.FRIENDS -> {
                    setInitItemValues(tvFriendsCount, tvFriendsPrice, item.value)
                    ivFriends.setOnClickListener { buyItem(item.key, tvFriendsCount, tvFriendsPrice, ivFriends) }
                }
                ItemName.TEAM -> {
                    setInitItemValues(tvTeamCount, tvTeamPrice, item.value)
                    ivTeam.setOnClickListener { buyItem(item.key, tvTeamCount, tvTeamPrice, ivTeam) }
                }
                ItemName.COMPANY -> {
                    setInitItemValues(tvCompanyCount, tvCompanyPrice, item.value)
                    ivCompany.setOnClickListener { buyItem(item.key, tvCompanyCount, tvCompanyPrice, ivCompany) }
                }
                else -> {
                    throw IllegalArgumentException("There is non-proper item in the shop")
                }
            }
        }
    }
    private fun buyItem(itemName: ItemName, tvCount: TextView, tvPrice: TextView, ivIcon: ImageView) {
        if(shop.buy(itemName)) {
            var itemCount = tvCount.text.toString().toInt()
            itemCount++
            tvCount.setText(itemCount.toString())
            val formattedPrice = formatPrice(shop.items[itemName]!!.getCurrentPrice())
            tvPrice.setText(formattedPrice)
            successAnimation(ivIcon)
        } else {
            failAnimation(ivIcon)
        }
    }

    private fun successAnimation(ivIcon: ImageView){
        val scaleUpAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_up)
        val scaleDownAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_down)
        ivIcon.startAnimation(scaleUpAnimation)
        ivIcon.startAnimation(scaleDownAnimation)
    }

    private fun failAnimation(ivIcon: ImageView) {
        val failAnimation = AnimationUtils.loadAnimation(this, R.anim.fail)
        ivIcon.startAnimation(failAnimation)
    }
}