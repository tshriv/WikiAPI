package com.ts.wikipages.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ts.wikipages.databinding.FragmentRandomPagesBinding
import com.ts.wikipages.viewModel

class FragmentRandomPages : Fragment() {
    lateinit var binding: FragmentRandomPagesBinding
    val viewModel: viewModel by activityViewModels()
    lateinit var adapter: RandomPageAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRandomPagesBinding.inflate(layoutInflater, container, false)

        adapter = RandomPageAdapter(viewModel, viewLifecycleOwner)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this.requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isRandomPageListChanged.observe(viewLifecycleOwner) {
            if (viewModel.isRandomPageListChanged.value == true) {
                adapter.notifyDataSetChanged()
                viewModel.isRandomPageListChanged.value = false
            }
        }
    }


}