package com.ts.wikipages.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ts.wikipages.R
import com.ts.wikipages.databinding.ItemViewImagesBinding
import com.ts.wikipages.viewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FeatureImageAdapter(val viewModel: viewModel) :
    RecyclerView.Adapter<FeatureImageAdapter.viewHolder>() {
    class viewHolder(val binding: ItemViewImagesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val binding = ItemViewImagesBinding.inflate(LayoutInflater.from(parent.context))
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        viewModel.imageList?.query?.pages?.pageList?.get(position)?.imageinfo?.url?.let {
            //   holder.binding.imageView.setImageDrawable(holder.itemView.resources.getDrawable(R.drawable.ic_broken_image))
            viewModel.viewModelScope.launch(Dispatchers.IO) {
                setImage(holder.binding.imageView, it, position)
            }
        }
        if (position == itemCount - 1) {
            viewModel.viewModelScope.launch { viewModel.continueLoadingImages() }
        }
    }

    private fun setImage(imgView: ImageView, url: String, pos: Int) {
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            url.let {
                Log.d("##setImageCalled", "setImageCalled $pos")
                val imgUri = it.toUri().buildUpon().scheme("https").build()
                imgView.load(imgUri) {
                    placeholder(R.drawable.loading_animation)
                    error(R.drawable.ic_broken_image)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        Log.d("@@imagecount", "${viewModel.imageList?.query?.pages?.pageList?.size!!}")
        return viewModel.imageList?.query?.pages?.pageList?.size!!
    }
}