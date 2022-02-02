package com.metehanbolat.gliserichomework.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "food_features")
data class FoodFeaturesModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val foodName: String,
    val glycemicIndex: String,
    val carbohydrates: String,
    val calories: String,
    val category: String,
    val favourite: Int
) : Parcelable