package com.ts.wikipages.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ts.wikipages.databinding.ItemViewCategoriesBinding
import com.ts.wikipages.viewModel

class CategoryListAdapter(val viewModel: viewModel) :
    RecyclerView.Adapter<CategoryListAdapter.viewHolder>() {
    lateinit var binding: ItemViewCategoriesBinding

    class viewHolder(val binding: ItemViewCategoriesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        binding = ItemViewCategoriesBinding.inflate(LayoutInflater.from(parent.context))
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.binding.textView2.text =
            viewModel.categoryList?.query?.allcategories?.get(position)?.category
    }

    override fun getItemCount(): Int {
        return viewModel.categoryList?.query?.allcategories?.size ?: 0
    }
}