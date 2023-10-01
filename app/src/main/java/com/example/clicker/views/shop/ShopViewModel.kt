package com.example.clicker.views.shop

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.clicker.Navigator
import com.example.clicker.model.game.GameRepository
import com.example.clicker.views.base.BaseViewModel
import com.example.clicker.views.base.PointsObserver

class ShopViewModel(
    val gameRepository: GameRepository,
    private val navigator: Navigator
): BaseViewModel(), PointsObserver {

    private val _currentPoints: MutableLiveData<Long> = MutableLiveData(gameRepository.currentPoints)
    val currentPoints: LiveData<Long> = _currentPoints

    init {
        gameRepository.addObserver(this)
    }

    override fun onPointsChanged(newPoints: Long) {
        _currentPoints.value = newPoints
    }

    fun back(supportFragmentManager: FragmentManager) {
        navigator.goBack(null, supportFragmentManager)
    }

}