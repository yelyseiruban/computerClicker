package com.example.clicker.views.startscreen

import android.app.Activity
import com.example.clicker.Navigator
import com.example.clicker.UiActions
import com.example.clicker.views.base.BaseViewModel
import com.example.clicker.views.clicker.ClickerFragment

class StartScreenViewModel(
    private val navigator: Navigator,
) : BaseViewModel() {

    fun startGame() {
        val screen = ClickerFragment.Screen()
        navigator.launch(screen)
    }

    fun finish(activity: Activity) {
        activity.finish()
    }

}