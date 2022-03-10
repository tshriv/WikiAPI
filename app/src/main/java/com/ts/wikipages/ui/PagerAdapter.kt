package com.ts.wikipages.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PagerAdapter(fragManager: FragmentManager) : FragmentPagerAdapter(fragManager) {
    override fun getCount(): Int = 3

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return FragmentImages()
            1 -> return FragmentCategories()
            2 -> return FragmentRandomPages()
            else -> return FragmentImages()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "Featured Images"
            1 -> return "Categories"
            2 -> return "Random Pages"
            else -> return "Feature images"
        }
    }
}