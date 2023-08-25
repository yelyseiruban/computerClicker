package com.example.clicker.activities

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.clicker.ClickAction
import com.example.clicker.GameState
import com.example.clicker.R
import com.example.clicker.StateAct
import com.example.clicker.formatNumber


class GameActivity : AppCompatActivity() {
    private lateinit var gameStateObj: GameState
    private lateinit var clickAction: ClickAction
    private lateinit var tvClicksCount: TextView
    private lateinit var tvClicksPerSecond: TextView
    private lateinit var animatedTextView: TextView
    private lateinit var stateAct: StateAct

    private val gameStateObserver: (GameState) -> Unit = { newState ->
        // Update your UI based on the newState
        updateUI(newState)
    }

    private fun updateUI(gameState: GameState) {
        tvClicksCount.text = gameState.pointsCount.toString()
        tvClicksPerSecond.text = gameState.pointsPerSecond.toString()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        stateAct = StateAct(this)

        setupGameState()
        setupInitState()

        val computer: ImageView = findViewById(R.id.clickedComputer)
        val scaleUpAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_up)
        val scaleDownAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_down)
        animatedTextView = findViewById(R.id.animatedTextView)

        computer.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val x = event.x
                val y = event.y

                // Update the position of the custom TextView
                val layoutParams = animatedTextView.layoutParams as RelativeLayout.LayoutParams
                layoutParams.leftMargin = x.toInt() - 40 // Update with desired X coordinate
                layoutParams.topMargin = y.toInt() - 20  // Update with desired Y coordinate
                animatedTextView.layoutParams = layoutParams
                animatedTextView.text = clickAction.gameState.pointsPerClick.toString()

                computer.startAnimation(scaleUpAnimation)
                computer.startAnimation(scaleDownAnimation)
                animateFadeTranslate()
                clickAction.clickOnComputer()
                tvClicksCount.text = formatNumber(clickAction.getPointsCount())

            }
            true
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
    private fun animateFadeTranslate() {
        val animationSet = AnimationSet(true)

        // Fade In Animation
        val fadeIn = AlphaAnimation(0f, 1f)
        fadeIn.duration = 200
        animationSet.addAnimation(fadeIn)

        // Scale Animation
        val scaleUp = ScaleAnimation(
            1f, 1.1f,   // X-axis scale from 0.5 to 1.0
            1f, 1.1f,   // Y-axis scale from 0.5 to 1.0
            Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point x
            Animation.RELATIVE_TO_SELF, 0.5f  // Pivot point y
        )
        scaleUp.duration = 200
        animationSet.addAnimation(scaleUp)

        // Scale Down Animation
        val scaleDown = ScaleAnimation(
            1.1f, 1f,   // X-axis scale from 1.0 to 0.5
            1.1f, 1f,   // Y-axis scale from 1.0 to 0.5
            Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point x
            Animation.RELATIVE_TO_SELF, 0.5f  // Pivot point y
        )
        scaleDown.duration = 200
        scaleDown.startOffset = 400 // Delay after initial animations
        animationSet.addAnimation(scaleDown)

        val fadeOut = AlphaAnimation(1f, 0f)
        fadeOut.duration = 200
        fadeOut.startOffset = 400
        animationSet.addAnimation(fadeOut)

        // Set Animation Listener
        animationSet.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                animatedTextView.visibility = TextView.VISIBLE
            }

            override fun onAnimationEnd(animation: Animation?) {
                animatedTextView.visibility = TextView.INVISIBLE
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })

        animatedTextView.startAnimation(animationSet)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        stateAct.save()
    }


    private fun setupGameState() {
        gameStateObj = stateAct.load()
        gameStateObj.addObserver(gameStateObserver)
        clickAction = ClickAction(gameStateObj)
    }

    private fun setupInitState() {
        tvClicksCount = findViewById(R.id.clicksCount)
        tvClicksCount.text = formatNumber(gameStateObj.pointsCount)
        tvClicksPerSecond = findViewById(R.id.clicksPerSecond)
        tvClicksPerSecond.text = formatNumber(gameStateObj.pointsPerSecond.toLong())
    }

}