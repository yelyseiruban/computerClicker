package com.example.clicker.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.clicker.R

class AchievementsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achievements)

        val btnBack: Button = findViewById(R.id.btnBack)
        btnBack.setOnClickListener {
            val startScreenIntent = Intent(this, StartScreenActivity::class.java)
            startActivity(startScreenIntent)
        }
    }
}