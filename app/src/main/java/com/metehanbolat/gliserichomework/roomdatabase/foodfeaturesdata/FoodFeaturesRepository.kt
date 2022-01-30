package com.metehanbolat.gliserichomework.roomdatabase.foodfeaturesdata

import androidx.lifecycle.LiveData
import com.metehanbolat.gliserichomework.roommodel.FoodFeaturesModel
import com.metehanbolat.gliserichomework.roommodel.CategoryModel
import com.metehanbolat.gliserichomework.roomrelations.CategoryWithFoodFeatures

class FoodFeaturesRepository(private val foodFeaturesDao: FoodFeaturesDao) {

    val readAllData: LiveData<List<FoodFeaturesModel>> = foodFeaturesDao.readAllData()

    suspend fun addFoodFeatures(foodFeaturesModel: FoodFeaturesModel) {
        foodFeaturesDao.addFoodFeatures(foodFeaturesModel)
    }

    suspend fun addTitle(categoryModel: CategoryModel) {
        foodFeaturesDao.addTitle(categoryModel)
    }

    suspend fun getTitleWithFoodFeatures(category: String) : List<CategoryWithFoodFeatures>{
        return foodFeaturesDao.getCategoryWithFoodFeatures(category)
    }
}