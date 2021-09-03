package com.example.foody.data

import com.example.foody.data.database.RecipesDao
import com.example.foody.data.database.entities.FavoritesEntity
import com.example.foody.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao
) {

    fun readRecipes(): Flow<List<RecipesEntity>> {
        return recipesDao.readRecipes()
    }

    fun readFavRecipes(): Flow<List<FavoritesEntity>> {
        return recipesDao.readFavRecipes()
    }

    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDao.insertRecipes(recipesEntity)
    }

    suspend fun insertFavRecipes(favoritesEntity: FavoritesEntity) {
        recipesDao.insertFavRecipes(favoritesEntity)
    }

    suspend fun deleteFavRecipe(favoritesEntity: FavoritesEntity) {
        recipesDao.deleteFavRecipe(favoritesEntity)
    }

    suspend fun deleteFavRecipes() {
        recipesDao.deleteAllFavRecipes()
    }

}