package com.metehanbolat.gliserichomework.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.metehanbolat.gliserichomework.databinding.FoodFeaturesRowBinding
import com.metehanbolat.gliserichomework.roommodel.FoodFeaturesModel

class FoodFeaturesRecyclerAdapter: RecyclerView.Adapter<FoodFeaturesRecyclerAdapter.FoodFeaturesViewHolder>() {

    private var foodFeaturesList = emptyList<FoodFeaturesModel>()

    class FoodFeaturesViewHolder(val binding: FoodFeaturesRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodFeaturesViewHolder {
        val binding = FoodFeaturesRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodFeaturesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodFeaturesViewHolder, position: Int) {
        val currentItem = foodFeaturesList[position]
        holder.binding.foodName.text = currentItem.foodName
        holder.binding.glycemicIndex.text = currentItem.glycemicIndex
        holder.binding.carbohydrates.text = currentItem.carbohydrates
        holder.binding.calories.text = currentItem.calories
    }

    override fun getItemCount(): Int {
        return foodFeaturesList.size
    }

    fun setData(foodFeatures: List<FoodFeaturesModel>) {
        this.foodFeaturesList = foodFeatures
        notifyDataSetChanged()
    }
}