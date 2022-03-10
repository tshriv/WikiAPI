package com.ts.wikipages.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ts.wikipages.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.pager.adapter = PagerAdapter(supportFragmentManager)
        binding.tabLayout.setupWithViewPager(binding.pager)
        setContentView(binding.root)
    }
}