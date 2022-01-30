package com.metehanbolat.gliserichomework.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_features_category")
data class CategoryModel(
    @PrimaryKey(autoGenerate = false)
    val category: String,
    val index: Int
)
