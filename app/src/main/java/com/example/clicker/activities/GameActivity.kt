package com.example.clicker.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.clicker.ClickAction
import com.example.clicker.GameState
import com.example.clicker.R
import com.example.clicker.StateAct
import com.google.gson.Gson
import java.io.File


class GameActivity : AppCompatActivity() {
    private lateinit var gameStateObj: GameState
    private lateinit var clickAction: ClickAction
    private lateinit var tvClicksCount: TextView
    private lateinit var stateAct: StateAct

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        stateAct = StateAct(this)

        setupGameState()
        setupClickersCount()

        val computer: ImageView = findViewById(R.id.clickedComputer)
        computer.setOnClickListener {

            clickAction.clickOnComputer()
            tvClicksCount.setText(clickAction.getPointsCount().toString())

        }
        val btnShop: Button = findViewById(R.id.btnShop)
        btnShop.setOnClickListener {
            stateAct.save()
            val intent = Intent(this, ShopActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        stateAct.save()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        stateAct.save()
    }

    private fun setupGameState() {
        gameStateObj = stateAct.load()
        clickAction = ClickAction(gameStateObj)
    }

    private fun setupClickersCount() {
        tvClicksCount = findViewById(R.id.clicksCount)
        tvClicksCount.setText(gameStateObj.pointsCount.toString())
    }

}