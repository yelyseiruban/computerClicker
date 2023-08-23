package com.example.clicker.activities

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.clicker.ClickAction
import com.example.clicker.GameState
import com.example.clicker.R
import com.example.clicker.StateAct


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
        val scaleUpAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_up)
        val scaleDownAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_down)
        computer.setOnClickListener {
            computer.startAnimation(scaleUpAnimation)
            computer.startAnimation(scaleDownAnimation)
            clickAction.clickOnComputer()
            tvClicksCount.setText(clickAction.getPointsCount().toString())

        }
        val btnShop: Button = findViewById(R.id.btnShop)
        btnShop.setOnClickListener {
            stateAct.save()
            val shopIntent = Intent(this, ShopActivity::class.java)
            startActivity(shopIntent)
        }

        val startScreenIntent = Intent(this, StartScreenActivity::class.java)
        val btnBack: Button = findViewById(R.id.btnBack)
        btnBack.setOnClickListener {
            startActivity(startScreenIntent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
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