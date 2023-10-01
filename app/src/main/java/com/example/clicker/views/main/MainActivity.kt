package com.example.clicker.views.main

import android.app.AlertDialog
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.clicker.App
import com.example.clicker.R
import com.example.clicker.views.startscreen.StartScreenFragment

class MainActivity : AppCompatActivity() {

    private val activityViewModel by viewModels<MainViewModel> { ViewModelProvider.AndroidViewModelFactory(application) }
    private lateinit var app: App

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as App
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            activityViewModel.launchFragment(
                activity = this,
                screen = StartScreenFragment.Screen(),
                addToBackStack = false
            )
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                app.saveGameState()
                Log.d("Back", supportFragmentManager.fragments.size.toString())
                val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view)

                if (fragment is StartScreenFragment) {
                    showDialogView()
                }

                activityViewModel.goBack(null, supportFragmentManager)
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onResume() {
        super.onResume()
        activityViewModel.whenActivityActive.resource = this
    }

    override fun onPause() {
        super.onPause()
        app.saveGameState()
        activityViewModel.whenActivityActive.resource = null
    }

    override fun onStop() {
        super.onStop()
        app.saveGameState()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        app.saveGameState()
    }
    private fun showDialogView(){
        val dialogView = layoutInflater.inflate(R.layout.custom_dialog_layout, null)
        val alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        val negativeButton = dialogView.findViewById<Button>(R.id.negativeButton)
        val positiveButton = dialogView.findViewById<Button>(R.id.positiveButton)

        negativeButton.setOnClickListener {
            alertDialog.dismiss()
        }

        positiveButton.setOnClickListener {
            finish()
        }

        alertDialog.show()

    }
}