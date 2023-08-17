package com.example.clicker.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.clicker.R

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val btnShop: Button = findViewById(R.id.btnShop)
        btnShop.setOnClickListener {
            val intent = Intent(this, ShopActivity::class.java)
        }
    }
}