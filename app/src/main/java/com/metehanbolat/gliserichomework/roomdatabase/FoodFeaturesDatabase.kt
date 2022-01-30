package com.metehanbolat.gliserichomework.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.metehanbolat.gliserichomework.roommodel.FoodFeaturesModel
import com.metehanbolat.gliserichomework.roommodel.CategoryModel
import com.metehanbolat.gliserichomework.roomdatabase.foodfeaturesdata.FoodFeaturesDao

@Database(entities = [CategoryModel::class, FoodFeaturesModel::class], version = 1, exportSchema = false)
abstract class FoodFeaturesDatabase: RoomDatabase() {

    abstract fun foodFeaturesDao(): FoodFeaturesDao

    companion object {
        @Volatile
        private var INSTANCE: FoodFeaturesDatabase? = null

        fun getDatabase(context: Context): FoodFeaturesDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FoodFeaturesDatabase::class.java,
                    "food_features_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}