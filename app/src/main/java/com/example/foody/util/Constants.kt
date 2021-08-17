package com.example.foody.util

class Constants {

    companion object {

        const val API_KEY = "def03f97eb664436ac3a62c793e582a7"
        const val BASE_URL = "https://api.spoonacular.com"

        //API Query Keys

        const val QUERRY_NUMBER = "number"
        const val QUERRY_API_KEY = "apiKey"
        const val QUERRY_TYPE = "type"
        const val QUERRY_DIET = "diet"
        const val QUERRY_ADD_RECIPE_INFORMATION = "addRecipeInformation"
        const val QUERRY_FILL_INGREDIENTS = "fillIngredients"

        //ROOM Database
        const val DATABASE_NAME = "recipes_database"
        const val RECIPES_TABLE = "recipes_table"

        //Bottom Sheet and Preferences
        const val DEFAULT_RECIPES_NUMBER = "50"
        const val  DEFAULT_MEAL_TYPE = "main course"
        const val  DEFAULT_DIET_TYPE = "gluten free"

        const val PREFERENCES_NAME = "foody_preferences"
        const val PREFERENCES_MEAL_TYPE = "mealType"
        const val PREFERENCES_MEAL_TYPE_ID = "mealTypeId"
        const val PREFERENCES_DIET_TYPE = "dietType"
        const val PREFERENCES_DIET_TYPE_ID = "dietTypeId"
    }

}