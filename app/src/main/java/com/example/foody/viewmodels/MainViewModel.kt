package com.example.foody.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.foody.data.database.entities.FavoritesEntity
import com.example.foody.data.database.entities.FoodTriviaEntity
import com.example.foody.data.database.entities.RecipesEntity
import com.example.foody.data.network.Repository
import com.example.foody.models.FoodRecipe
import com.example.foody.models.FoodTrivia
import com.example.foody.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

class MainViewModel @ViewModelInject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {


    /** ROOM DATABASE aka Local Database*/

    val readRecipes: LiveData<List<RecipesEntity>> = repository.local.readRecipes().asLiveData()
    val readFavRecipes: LiveData<List<FavoritesEntity>> = repository.local.readFavRecipes().asLiveData()
    val readFoodTrivia: LiveData<List<FoodTriviaEntity>> = repository.local.readFoodTrivia().asLiveData()

    private fun insertRecipes(recipesEntity: RecipesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertRecipes(recipesEntity)
        }

    fun insertFavRecipe(favoritesEntity: FavoritesEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertFavRecipes(favoritesEntity)
        }
    }

    fun insertFoodTrivia(foodTriviaEntity: FoodTriviaEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertFoodTrivia(foodTriviaEntity)
        }

    fun deleteFavRecipe(favoritesEntity: FavoritesEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteFavRecipe(favoritesEntity)
        }
    }

    fun deleteAllFavRecipe() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteFavRecipes()
        }
    }

    /** RETROFIT  aka Data straight from the api*/
    var recipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
    var searchedRecipeResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
    var foodTriviaResponse: MutableLiveData<NetworkResult<FoodTrivia>> = MutableLiveData()

    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
    }

    fun searchRecipes(searchQuery: Map<String,String>) = viewModelScope.launch {
        searchRecipesSafeCall(searchQuery)
    }

    fun getFoodTrivia(apiKey: String) = viewModelScope.launch {
        getFoodTriviaSafeCall(apiKey)
    }


    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        recipesResponse.value = NetworkResult.Loading()
        if(hasInternetConenction()) {
            try {
                val response = repository.remote.getRecipes(queries)
                recipesResponse.value = handleFoodRecipesResponse(response)

                val foodRecipe = recipesResponse.value!!.data //!!
                if(foodRecipe != null) {
                    offlineCacheRecipes(foodRecipe)
                }
            } catch (e: Exception) {
                recipesResponse.value = NetworkResult.Error("Recipes not found.")

            }
        } else {
            recipesResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private suspend fun searchRecipesSafeCall(searchQuery: Map<String, String>) {
        searchedRecipeResponse.value = NetworkResult.Loading()
        if(hasInternetConenction()) {
            try {
                val response = repository.remote.searchRecipes(searchQuery)
                searchedRecipeResponse.value = handleFoodRecipesResponse(response)
            } catch (e: Exception) {
                searchedRecipeResponse.value = NetworkResult.Error("Recipes not found.")
            }
        } else {
            searchedRecipeResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private suspend fun getFoodTriviaSafeCall(apiKey: String) {
        foodTriviaResponse.value = NetworkResult.Loading()
        if(hasInternetConenction()) {
            try {
                val response = repository.remote.getFoodTrivia(apiKey)
                foodTriviaResponse.value = handleFoodTriviaResponse(response)

                val foodTrivia = foodTriviaResponse.value!!.data
                if(foodTrivia != null) offlineCacheFoodTrivia(foodTrivia)
            } catch (e: Exception) {
                foodTriviaResponse.value = NetworkResult.Error("Recipes not found.")
            }
        } else {
            foodTriviaResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private fun offlineCacheRecipes(foodRecipe: FoodRecipe) {
        val recipesEntity = RecipesEntity(foodRecipe)
        insertRecipes(recipesEntity)
    }

    private fun offlineCacheFoodTrivia(foodTrivia: FoodTrivia) {
        val foodTriviaEntity = FoodTriviaEntity(foodTrivia)
        insertFoodTrivia(foodTriviaEntity)
    }

    private fun handleFoodRecipesResponse(response: Response<FoodRecipe>): NetworkResult<FoodRecipe>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited.")
            }
            response.body()!!.results.isNullOrEmpty() -> {
                return NetworkResult.Error(" Recipes not found")
            }
            response.isSuccessful -> {
                val foodRecipes =response.body()
                return NetworkResult.Success(foodRecipes!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }


    private fun handleFoodTriviaResponse(response: Response<FoodTrivia>): NetworkResult<FoodTrivia>? {
        return when {
            response.message().toString().contains("timeout") -> {
                NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                NetworkResult.Error("API Key Limited.")
            }
            response.isSuccessful -> {
                val foodTrivia =response.body()
                NetworkResult.Success(foodTrivia!!)
            }
            else -> {
                NetworkResult.Error(response.message())
            }
        }
    }


    private fun hasInternetConenction(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}