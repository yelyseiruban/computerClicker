package com.example.clicker.activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import com.example.clicker.R

class StartScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_screen)

        val startGameActivity = {
            val startGameIntent = Intent(this, GameActivity::class.java)
            startActivity(startGameIntent)
        }
        val btnStart: Button = findViewById(R.id.btnStart)
        btnStart.setOnClickListener{
            startGameActivity()
        }
        val computerPic: ImageView = findViewById(R.id.ivStartScreen)
        computerPic.setOnClickListener {
            startGameActivity()
        }

        val btnAchievements: Button = findViewById(R.id.btnAchievements)
        btnAchievements.setOnClickListener {
            val startAchievementsIntent = Intent(this, AchievementsActivity::class.java)
            startActivity(startAchievementsIntent)
        }

        val btnExit: Button = findViewById(R.id.btnExit)
        btnExit.setOnClickListener{
            finish()
            System.exit(0)
        }
        val context = this
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showDialogView()
            }
        })
    }
    private fun showDialogView(){
        val dialogView = layoutInflater.inflate(R.layout.custom_dialog_layout, null)
        val alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        val negativeButton = dialogView.findViewById<Button>(R.id.negativeButton)
        val positiveButton = dialogView.findViewById<Button>(R.id.positiveButton)

        negativeButton.setOnClickListener {
            // Handle negative button click
            alertDialog.dismiss()
        }

        positiveButton.setOnClickListener {
            // Handle positive button click
            alertDialog.dismiss()
        }

        alertDialog.show()

    }
}