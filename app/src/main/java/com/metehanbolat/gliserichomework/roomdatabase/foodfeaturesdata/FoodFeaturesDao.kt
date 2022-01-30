package com.metehanbolat.gliserichomework.roomdatabase.foodfeaturesdata

import androidx.lifecycle.LiveData
import androidx.room.*
import com.metehanbolat.gliserichomework.roommodel.FoodFeaturesModel
import com.metehanbolat.gliserichomework.roommodel.CategoryModel
import com.metehanbolat.gliserichomework.roomrelations.CategoryWithFoodFeatures

@Dao
interface FoodFeaturesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFoodFeatures(foodFeaturesModel: FoodFeaturesModel)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTitle(categoryModel: CategoryModel)

    @Query("SELECT * FROM food_features ORDER BY id ASC")
    fun readAllData(): LiveData<List<FoodFeaturesModel>>

    @Transaction
    @Query("SELECT * FROM food_features_category WHERE category = :category")
    suspend fun getCategoryWithFoodFeatures(category: String): List<CategoryWithFoodFeatures>

}