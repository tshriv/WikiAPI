package com.ts.wikipages.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ts.wikipages.databinding.FragmentImagesBinding
import com.ts.wikipages.viewModel

class FragmentImages : Fragment() {

    lateinit var binding: FragmentImagesBinding
    val viewmodel: viewModel by activityViewModels()
    lateinit var adapter: FeatureImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImagesBinding.inflate(layoutInflater, container, false)

        //setup RecyclerView

        binding.recyclerview.layoutManager = LinearLayoutManager(this.requireContext())
        adapter = FeatureImageAdapter(viewmodel)

        binding.recyclerview.adapter = adapter


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel.isimageListchanged.observe(viewLifecycleOwner) {
            if (viewmodel.isimageListchanged.value == true) {
                adapter.notifyItemInserted(viewmodel.imageListSize)
                viewmodel.imageListSize = viewmodel.imageList?.query?.pages?.pageList?.size!!
                viewmodel.isimageListchanged.value = false
            }
        }
    }

}
