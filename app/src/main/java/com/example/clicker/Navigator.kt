package com.example.clicker

import androidx.fragment.app.FragmentManager
import com.example.clicker.views.base.BaseScreen

interface Navigator {

    fun launch(screen: BaseScreen)

    fun goBack(result: Any? = null, supportFragmentManager: FragmentManager)

}