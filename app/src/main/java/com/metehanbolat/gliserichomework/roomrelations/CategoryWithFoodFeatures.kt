package com.metehanbolat.gliserichomework.roomrelations

import androidx.room.Embedded
import androidx.room.Relation
import com.metehanbolat.gliserichomework.roommodel.FoodFeaturesModel
import com.metehanbolat.gliserichomework.roommodel.CategoryModel

data class CategoryWithFoodFeatures(
    @Embedded val category: CategoryModel,
    @Relation(
        parentColumn = "category",
        entityColumn = "category"
    )
    val foodFeatures: List<FoodFeaturesModel>
)
