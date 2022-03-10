package com.ts.wikipages.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.ts.wikipages.databinding.ItemViewRandomPagesBinding
import com.ts.wikipages.viewModel
import kotlinx.coroutines.launch

class RandomPageAdapter(val viewModel: viewModel, val viewLifecycleOwner: LifecycleOwner) :
    RecyclerView.Adapter<RandomPageAdapter.viewHolder>() {
    class viewHolder(val binding: ItemViewRandomPagesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val binding: ItemViewRandomPagesBinding =
            ItemViewRandomPagesBinding.inflate(LayoutInflater.from(parent.context))
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val title = viewModel.randomPages?.pages?.get(position)?.title ?: "null"
        holder.binding.titleTv.text = title
        viewModel.randomPages?.pages?.get(position)?.content?.observe(viewLifecycleOwner) {
            holder.binding.descriptionTv.text = it
        }
        viewModel.viewModelScope.launch {
            viewModel.loadRandomPagedescription(title, position)
        }

        if (position == itemCount - 1) {
            viewModel.viewModelScope.launch { viewModel.loadRandomPagesContinued() }
        }
    }

    override fun getItemCount(): Int {
        return viewModel.randomPages?.pages?.size ?: 0
    }
}