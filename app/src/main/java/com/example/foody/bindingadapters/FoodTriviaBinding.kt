package com.example.foody.bindingadapters

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.foody.data.database.entities.FoodTriviaEntity
import com.example.foody.models.FoodTrivia
import com.example.foody.util.NetworkResult
import com.google.android.material.card.MaterialCardView

class FoodTriviaBinding {

    companion object {

        @BindingAdapter("readApiResponse3","readDatabase3", requireAll = false)
        @JvmStatic
        fun SetCardAndProgressVisibility(
            view: View,
            apiResponse: NetworkResult<FoodTrivia>?,
            database: List<FoodTriviaEntity>?
        ) {
            when(apiResponse) {
                is NetworkResult.Loading -> {
                    when(view) {
                        is ProgressBar -> {
                            view.visibility = View.VISIBLE
                        }
                        is MaterialCardView -> {
                            view.visibility = View.INVISIBLE
                        }
                    }
                }
                is NetworkResult.Error -> {
                    when(view) {
                        is ProgressBar -> {
                            view.visibility = View.INVISIBLE
                        }
                        is MaterialCardView -> {
                            view.visibility = View.VISIBLE
                            if(database != null) {
                                if(database.isEmpty()) {
                                    view.visibility = View.INVISIBLE
                                }
                            }
                        }
                    }
                }
                is NetworkResult.Success -> {
                    when(view) {
                        is ProgressBar -> {
                            view.visibility = View.INVISIBLE
                        }
                        is MaterialCardView -> {
                            view.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }

        @BindingAdapter("readApiResponse4","readDatabase4", requireAll = true)
        @JvmStatic
        fun setErrorViewsVisibility(
            view: View,
            apiResponse: NetworkResult<FoodTrivia>?,
            database: List<FoodTriviaEntity>?
        ){
            if(database != null) {
                if(database.isEmpty()) {
                    view.visibility = View.VISIBLE
                    if(view is TextView) {
                        if(apiResponse != null) {
                            view.text = apiResponse.message.toString()
                        }
                    }
                }
            }
            if(apiResponse is NetworkResult.Success){
                view.visibility = View.INVISIBLE
            }
        }
    }

}