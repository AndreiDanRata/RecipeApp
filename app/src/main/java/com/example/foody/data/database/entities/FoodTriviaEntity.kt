package com.example.foody.data.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foody.models.FoodTrivia
import com.example.foody.util.Constants.Companion.FOOD_TRIVIA_TABLE

@Entity(tableName = FOOD_TRIVIA_TABLE)
class FoodTriviaEntity(
    @Embedded
    var foodTrivia: FoodTrivia
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}