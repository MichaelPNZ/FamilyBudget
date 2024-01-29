package com.example.familybudget.ui.analyticsScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.familybudget.databinding.FragmentAnalyticsBinding

class AnalyticsFragment : Fragment() {

    private val binding by lazy {
        FragmentAnalyticsBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}