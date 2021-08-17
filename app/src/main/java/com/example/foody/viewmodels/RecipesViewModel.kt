package com.example.foody.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.foody.data.DataStoreRepository
import com.example.foody.data.MealAndDietType
import com.example.foody.util.Constants
import com.example.foody.util.Constants.Companion.API_KEY
import com.example.foody.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.foody.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.foody.util.Constants.Companion.DEFAULT_RECIPES_NUMBER
import com.example.foody.util.Constants.Companion.QUERRY_ADD_RECIPE_INFORMATION
import com.example.foody.util.Constants.Companion.QUERRY_API_KEY
import com.example.foody.util.Constants.Companion.QUERRY_DIET
import com.example.foody.util.Constants.Companion.QUERRY_FILL_INGREDIENTS
import com.example.foody.util.Constants.Companion.QUERRY_NUMBER
import com.example.foody.util.Constants.Companion.QUERRY_TYPE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RecipesViewModel @ViewModelInject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
    ) : AndroidViewModel(application) {

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    val readMealAndDietType = dataStoreRepository.readMealAndDietType

    fun saveMealAndDietType(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveMealAndDietType(mealType, mealTypeId, dietType, dietTypeId)
        }
    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        viewModelScope.launch {
            readMealAndDietType.collect { value ->
                mealType = value.selectedMealType
                dietType = value.selectedDietType
            }
        }

        queries[QUERRY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERRY_API_KEY] = API_KEY
        queries[QUERRY_TYPE] = mealType
        queries[QUERRY_DIET] = dietType
        queries[QUERRY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERRY_FILL_INGREDIENTS] = "true"

        return queries
    }
}