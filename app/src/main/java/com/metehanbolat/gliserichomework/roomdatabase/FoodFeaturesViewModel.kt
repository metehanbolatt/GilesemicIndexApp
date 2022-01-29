package com.metehanbolat.gliserichomework.roomdatabase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FoodFeaturesViewModel(application: Application) : AndroidViewModel(application) {

     private val readAllData: LiveData<List<FoodFeaturesModel>>
     private val repository: FoodFeaturesRepository

     init {
         val foodFeaturesDao = FoodFeaturesDatabase.getDatabase(application).foodFeaturesDao()
         repository = FoodFeaturesRepository(foodFeaturesDao)
         readAllData = repository.readAllData
     }

    fun addFoodFeatures(foodFeaturesModel: FoodFeaturesModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFoodFeatures(foodFeaturesModel)
        }
    }
}