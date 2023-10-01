package com.example.clicker.views.main

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import com.example.clicker.App
import com.example.clicker.GameStatePreferences
import com.example.clicker.Navigator
import com.example.clicker.R
import com.example.clicker.UiActions
import com.example.clicker.utils.Event
import com.example.clicker.utils.ResourceActions
import com.example.clicker.views.base.BaseScreen
import com.example.clicker.views.base.LiveEvent
import com.example.clicker.views.base.MutableLiveEvent

class MainViewModel(
    private val application: Application
) : AndroidViewModel(application), Navigator, UiActions {

    init {
        Log.d("UPLOAD", "here")
        val gameStatePreferences = GameStatePreferences(application)
        gameStatePreferences.upload()
    }

    val whenActivityActive = ResourceActions<MainActivity>()

    private val _result = MutableLiveEvent<Any>()
    override fun launch(screen: BaseScreen) = whenActivityActive {
        launchFragment(it, screen)
    }

    override fun goBack(result: Any?, supportFragmentManager: FragmentManager) = whenActivityActive {
        (application as App).saveGameState()
        if (result != null) {
            _result.value = Event(result)
        }
        supportFragmentManager.popBackStack()
    }

    override fun toast(message: String) {
        Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show()
    }

    override fun getString(messageRes: Int, vararg args: Any): String {
        return getApplication<App>().getString(messageRes, *args)
    }

    fun launchFragment(activity: MainActivity, screen: BaseScreen, addToBackStack: Boolean = true) {
        val fragment = screen.javaClass.enclosingClass.newInstance() as Fragment
        fragment.arguments = bundleOf("ARG_SCREEN" to screen)
        val transaction = activity.supportFragmentManager.beginTransaction()
        if (addToBackStack) transaction.addToBackStack(null)
        transaction
            .replace(R.id.fragment_container_view, fragment)
            .commit()
    }

    override fun onCleared() {
        super.onCleared()
        whenActivityActive.clear()
    }

}