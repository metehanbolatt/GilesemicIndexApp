package com.metehanbolat.gliserichomework.repository

import androidx.lifecycle.LiveData
import com.metehanbolat.gliserichomework.model.FoodFeaturesModel
import com.metehanbolat.gliserichomework.model.CategoryModel
import com.metehanbolat.gliserichomework.roomdatabase.FoodFeaturesDao
import com.metehanbolat.gliserichomework.roomdatabase.CategoryWithFoodFeatures

class FoodFeaturesRepository(private val foodFeaturesDao: FoodFeaturesDao) {

    val readAllData: LiveData<List<FoodFeaturesModel>> = foodFeaturesDao.readAllData()

    suspend fun addFoodFeatures(foodFeaturesModel: FoodFeaturesModel) {
        foodFeaturesDao.addFoodFeatures(foodFeaturesModel)
    }

    suspend fun updateFoodFeatures(foodFeaturesModel: FoodFeaturesModel) {
        foodFeaturesDao.updateFoodFeatures(foodFeaturesModel)
    }

    suspend fun deleteFoodFeatures(foodFeaturesModel: FoodFeaturesModel) {
        foodFeaturesDao.deleteFoodFeatures(foodFeaturesModel)
    }

    fun searchDatabase(searchQuery: String): LiveData<List<FoodFeaturesModel>> {
        return foodFeaturesDao.searchDatabase(searchQuery)
    }

    suspend fun addTitle(categoryModel: CategoryModel) {
        foodFeaturesDao.addTitle(categoryModel)
    }

    suspend fun getTitleWithFoodFeatures(category: String) : List<CategoryWithFoodFeatures>{
        return foodFeaturesDao.getCategoryWithFoodFeatures(category)
    }
}