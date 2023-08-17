package com.example.clicker.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.clicker.R

class StartScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_screen)

        val btnStart: Button = findViewById(R.id.btnStart)
        val btnExit: Button = findViewById(R.id.btnExit)

        btnStart.setOnClickListener{
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }

        btnExit.setOnClickListener{
            finish()
            System.exit(0)
        }
    }
}