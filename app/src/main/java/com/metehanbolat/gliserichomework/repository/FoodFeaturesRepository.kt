package com.metehanbolat.gliserichomework.repository

import androidx.lifecycle.LiveData
import com.metehanbolat.gliserichomework.model.FoodFeaturesModel
import com.metehanbolat.gliserichomework.model.CategoryModel
import com.metehanbolat.gliserichomework.roomdatabase.FoodFeaturesDao
import com.metehanbolat.gliserichomework.model.CategoryWithFoodFeatures

class FoodFeaturesRepository(private val foodFeaturesDao: FoodFeaturesDao) {

    val readAllData: LiveData<List<FoodFeaturesModel>> = foodFeaturesDao.readAllData()
    val readAllCategory: LiveData<List<CategoryModel>> = foodFeaturesDao.readAllCategories()

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

    suspend fun getTitleWithFoodFeatures(category: String) : List<CategoryWithFoodFeatures>{
        return foodFeaturesDao.getCategoryWithFoodFeatures(category)
    }

    suspend fun addCategory(categoryModel: CategoryModel) {
        foodFeaturesDao.addCategory(categoryModel)
    }

    suspend fun deleteCategory(categoryModel: CategoryModel) {
        foodFeaturesDao.deleteCategory(categoryModel)
    }

    suspend fun deleteFoodFeaturesWithCategory(category: String) {
        foodFeaturesDao.deleteFoodFeaturesWithCategory(category)
    }

    suspend fun getFavouriteFoodFeatures(): List<FoodFeaturesModel> {
        return foodFeaturesDao.getFavouriteFoodFeatures()
    }
}