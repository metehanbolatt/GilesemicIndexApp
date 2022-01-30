package com.metehanbolat.gliserichomework.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.metehanbolat.gliserichomework.model.FoodFeaturesModel
import com.metehanbolat.gliserichomework.model.CategoryModel

@Dao
interface FoodFeaturesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFoodFeatures(foodFeaturesModel: FoodFeaturesModel)

    @Update
    suspend fun updateFoodFeatures(foodFeaturesModel: FoodFeaturesModel)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTitle(categoryModel: CategoryModel)

    @Query("SELECT * FROM food_features ORDER BY id ASC")
    fun readAllData(): LiveData<List<FoodFeaturesModel>>

    @Transaction
    @Query("SELECT * FROM food_features_category WHERE category = :category")
    suspend fun getCategoryWithFoodFeatures(category: String): List<CategoryWithFoodFeatures>

}