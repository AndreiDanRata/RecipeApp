package com.example.foody.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.foody.util.Constants
import com.example.foody.util.Constants.Companion.API_KEY
import com.example.foody.util.Constants.Companion.QUERRY_ADD_RECIPE_INFORMATION
import com.example.foody.util.Constants.Companion.QUERRY_API_KEY
import com.example.foody.util.Constants.Companion.QUERRY_DIET
import com.example.foody.util.Constants.Companion.QUERRY_FILL_INGREDIENTS
import com.example.foody.util.Constants.Companion.QUERRY_NUMBER
import com.example.foody.util.Constants.Companion.QUERRY_TYPE

class RecipesViewModel(application: Application) : AndroidViewModel(application) {

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        queries[QUERRY_NUMBER] = "50"
        queries[QUERRY_API_KEY] = API_KEY
        queries[QUERRY_TYPE] = "snack"
        queries[QUERRY_DIET] = "vegan"
        queries[QUERRY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERRY_FILL_INGREDIENTS] = "true"

        return queries
    }
}