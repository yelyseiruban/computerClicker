package com.example.clicker.views.clicker

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.clicker.Navigator
import com.example.clicker.model.game.GameRepository
import com.example.clicker.views.base.BaseViewModel
import com.example.clicker.views.base.PointsObserver
import com.example.clicker.views.shop.ShopFragment

class ClickerViewModel(
    private val gameRepository: GameRepository, private val navigator: Navigator
) : BaseViewModel(), PointsObserver {

    private val _currentPoints: MutableLiveData<Long> =
        MutableLiveData<Long>(gameRepository.currentPoints)
    val currentPoints: LiveData<Long> = _currentPoints

    private val _pointsPerSecond: MutableLiveData<Int> =
        MutableLiveData<Int>(gameRepository.getPointsPerSecond())
    val pointsPerSecond: LiveData<Int> = _pointsPerSecond

    init {
        gameRepository.addObserver(this)
    }

    override fun onPointsChanged(newPoints: Long) {
        _currentPoints.value = newPoints
    }

    fun clickComputer() {
        val pointsPerClick = gameRepository.getPointsPerClick()
        gameRepository.increasePoints(pointsPerClick.toLong())
        updateCurrentPoints()
    }

    fun back(supportFragmentManager: FragmentManager) {
        navigator.goBack(null, supportFragmentManager)
    }

    fun openShop() {
        navigator.launch(ShopFragment.Screen())
    }

    private fun updateCurrentPoints() {
        _currentPoints.value = gameRepository.currentPoints
    }

    private fun updatePointsPerSecond() {
        _pointsPerSecond.value = gameRepository.getPointsPerSecond()
    }

    fun updateLiveData() {
        updateCurrentPoints()
        updatePointsPerSecond()
    }

}