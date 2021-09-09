package com.example.foody.data.database

import androidx.room.*
import com.example.foody.data.database.entities.FavoritesEntity
import com.example.foody.data.database.entities.FoodTriviaEntity
import com.example.foody.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipesEntity: RecipesEntity)  //suspend for later coroutines


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavRecipes(favoritesEntity: FavoritesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodTrivia(foodTriviaEntity: FoodTriviaEntity)

    @Query("SELECT * FROM recipes_table ORDER BY id ASC")
    fun readRecipes(): Flow<List<RecipesEntity>>

    @Query("SELECT * FROM favorite_recipes_table ORDER BY id ASC")
    fun readFavRecipes(): Flow<List<FavoritesEntity>>

    @Query("SELECT * FROM food_trivia_table ORDER BY id ASC")
    fun readFoodTrivia(): Flow<List<FoodTriviaEntity>>

    @Delete
    suspend fun deleteFavRecipe(favoritesEntity: FavoritesEntity)

    @Query("DELETE FROM favorite_recipes_table")
    suspend fun deleteAllFavRecipes()
}