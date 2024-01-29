package com.example.familybudget.ui.homeScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.familybudget.databinding.FragmentHomeScreenBinding

class HomeScreenFragment : Fragment() {

//    private val recipeListFragmentArgs: RecipesListFragmentArgs by navArgs()
//private val viewModel: RecipesListViewModel by viewModels()
//    private val recipesListAdapter = RecipesListAdapter(this)
    private val binding by lazy {
        FragmentHomeScreenBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

}