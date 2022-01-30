package com.metehanbolat.gliserichomework.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.metehanbolat.gliserichomework.databinding.FoodFeaturesRowBinding
import com.metehanbolat.gliserichomework.model.FoodFeaturesModel
import com.metehanbolat.gliserichomework.view.fragment.MainFragmentDirections
import com.metehanbolat.gliserichomework.viewmodel.CommonViewModel

class FoodFeaturesRecyclerAdapter(private val commonViewModel: CommonViewModel): RecyclerView.Adapter<FoodFeaturesRecyclerAdapter.FoodFeaturesViewHolder>() {

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

        holder.binding.rowConstraint.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToUpdateFragment(currentItem)
            it.findNavController().navigate(action)
        }

        holder.binding.deleteButton.setOnClickListener {
            AlertDialog.Builder(it.context).apply {
                setPositiveButton("Delete") {_, _ ->
                    commonViewModel.deleteFoodFeatures(currentItem)
                    Snackbar.make(it, "${currentItem.foodName} was deleted.", Snackbar.LENGTH_SHORT).show()
                }
                setNegativeButton("Cancel") {_, _ -> }
                setTitle("Delete ${currentItem.foodName}")
                setMessage("Are you sure you want to delete ${currentItem.foodName}")
                create().show()
            }
        }
    }

    override fun getItemCount(): Int {
        return foodFeaturesList.size
    }

    fun setData(foodFeatures: List<FoodFeaturesModel>) {
        this.foodFeaturesList = foodFeatures
        notifyDataSetChanged()
    }
}