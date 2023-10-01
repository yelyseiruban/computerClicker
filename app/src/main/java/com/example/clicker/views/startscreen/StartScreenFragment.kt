package com.example.clicker.views.startscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.clicker.App
import com.example.clicker.databinding.FragmentStartScreenBinding
import com.example.clicker.views.base.BaseFragment
import com.example.clicker.views.base.BaseScreen
import com.example.clicker.views.base.screenViewModel

class StartScreenFragment() : BaseFragment() {

    class Screen : BaseScreen

    override val viewModel by screenViewModel<StartScreenViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentStartScreenBinding.inflate(inflater, container, false)

        binding.btnStart.setOnClickListener {
            viewModel.startGame()
        }

        binding.btnExit.setOnClickListener {
            (requireActivity().application as App).saveGameState()
            viewModel.finish(requireActivity())
        }

        return binding.root
    }
}