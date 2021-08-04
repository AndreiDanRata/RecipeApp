package com.example.foody.ui.fragments.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.foody.R
import kotlinx.android.synthetic.main.fragment_recipes.view.*


class RecipesFragment : Fragment() {
    //https://api.spoonacular.com/recipes/complexSearch?number=1&apiKey=def03f97eb664436ac3a62c793e582a7&type=drink&diet=vegan&addRecipeInformation=true&fillingredients=true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_recipes, container, false)



        view.shimmer_recycler_view.showShimmer()
        return view
    }
}
