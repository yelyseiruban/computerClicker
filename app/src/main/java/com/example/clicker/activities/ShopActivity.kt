package com.example.clicker.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import com.example.clicker.R
import com.example.clicker.StateAct

class ShopActivity : AppCompatActivity() {
    private lateinit var stateAct: StateAct
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)
        stateAct = StateAct(this)

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
}