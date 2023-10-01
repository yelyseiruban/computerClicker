package com.example.clicker.views.clicker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.clicker.R
import com.example.clicker.databinding.FragmentClickerBinding
import com.example.clicker.formatNumber
import com.example.clicker.views.base.BaseFragment
import com.example.clicker.views.base.BaseScreen
import com.example.clicker.views.base.screenViewModel

class ClickerFragment(): BaseFragment() {

    class Screen : BaseScreen

    override val viewModel by screenViewModel<ClickerViewModel>()
    private lateinit var binding: FragmentClickerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentClickerBinding.inflate(inflater, container, false)

        viewModel.currentPoints.observe(viewLifecycleOwner) {
            binding.clicksCount.text = formatNumber(it)
        }
        viewModel.pointsPerSecond.observe(viewLifecycleOwner) {
            binding.pointsPerSecond.text = formatNumber(it.toLong())
        }

        val clickAnimation: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.click)

        binding.clickedComputer.setOnClickListener{
            binding.clickedComputer.startAnimation(clickAnimation)
            viewModel.clickComputer()
        }


        binding.btnShop.setOnClickListener {
            viewModel.openShop()
        }

        binding.btnBack.setOnClickListener {
            viewModel.back(requireActivity().supportFragmentManager)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateLiveData()
    }
}