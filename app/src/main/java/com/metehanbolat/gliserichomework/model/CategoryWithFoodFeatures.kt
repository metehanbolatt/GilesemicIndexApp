package com.metehanbolat.gliserichomework.model

import androidx.room.Embedded
import androidx.room.Relation
import com.metehanbolat.gliserichomework.model.FoodFeaturesModel
import com.metehanbolat.gliserichomework.model.CategoryModel

data class CategoryWithFoodFeatures(
    @Embedded val category: CategoryModel,
    @Relation(
        parentColumn = "category",
        entityColumn = "category"
    )
    val foodFeatures: List<FoodFeaturesModel>
)
