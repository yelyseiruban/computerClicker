package com.example.clicker.views.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clicker.App
import com.example.clicker.databinding.FragmentShopBinding
import com.example.clicker.formatNumber
import com.example.clicker.views.base.BaseFragment
import com.example.clicker.views.base.BaseScreen
import com.example.clicker.views.base.screenViewModel

class ShopFragment() : BaseFragment() {

    class Screen : BaseScreen

    override val viewModel by screenViewModel<ShopViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentShopBinding.inflate(inflater, container, false)

        val app = requireActivity().application as App
        val repository = viewModel.gameRepository

        val recyclerView: RecyclerView = binding.listView
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        val adapter = ShopListAdapter(requireActivity(), repository)
        recyclerView.adapter = adapter

        viewModel.currentPoints.observe(viewLifecycleOwner) {
            binding.tvBalance.text = formatNumber(it)
        }

        binding.btnBack.setOnClickListener {
            viewModel.back(requireActivity().supportFragmentManager)
        }

        return binding.root
    }
}