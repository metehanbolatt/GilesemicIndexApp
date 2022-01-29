package com.metehanbolat.gliserichomework.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FoodFeaturesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFoodFeatures(foodFeaturesModel: FoodFeaturesModel)

    @Query("SELECT * FROM food_features ORDER BY id ASC")
    fun readAllData(): LiveData<List<FoodFeaturesModel>>


}