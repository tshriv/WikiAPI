package com.ts.wikipages.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ts.wikipages.databinding.FragmentCategoriesBinding
import com.ts.wikipages.viewModel

class FragmentCategories : Fragment() {

    lateinit var binding: FragmentCategoriesBinding
    val viewModel: viewModel by activityViewModels()
    lateinit var adapter: CategoryListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCategoriesBinding.inflate(layoutInflater, container, false)
        adapter = CategoryListAdapter(viewModel)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this.requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isCategoryListChanged.observe(viewLifecycleOwner) {
            if (viewModel.isCategoryListChanged.value == true) {
                adapter.notifyDataSetChanged()
                viewModel.isCategoryListChanged.value = false
            }
        }

    }

}