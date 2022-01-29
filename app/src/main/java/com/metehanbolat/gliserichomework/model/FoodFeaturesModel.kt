package com.metehanbolat.gliserichomework.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_features")
data class FoodFeaturesModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val foodName: String,
    val glycemicIndex: String,
    val carbohydrates: String,
    val calories: String
)