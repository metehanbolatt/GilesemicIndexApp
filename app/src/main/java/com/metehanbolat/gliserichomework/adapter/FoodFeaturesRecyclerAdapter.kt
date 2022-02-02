package com.metehanbolat.gliserichomework.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.metehanbolat.gliserichomework.R
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
        holder.binding.carbohydrate.text = currentItem.carbohydrates
        holder.binding.calorie.text = currentItem.calories

        if (currentItem.favourite == 1) {
            holder.binding.favouriteImage.setImageDrawable(ContextCompat.getDrawable(holder.binding.root.context, R.drawable.ic_red_favourite))
        } else {
            holder.binding.favouriteImage.setImageDrawable(ContextCompat.getDrawable(holder.binding.root.context, R.drawable.ic_red_favourite_border))
        }

        when(currentItem.glycemicIndex.toInt()) {
            0 -> holder.binding.indexImage.setImageDrawable(ContextCompat.getDrawable(holder.binding.root.context, R.drawable.a_0))
            in 1..10 -> holder.binding.indexImage.setImageDrawable(ContextCompat.getDrawable(holder.binding.root.context, R.drawable.a_10))
            in 11..20 -> holder.binding.indexImage.setImageDrawable(ContextCompat.getDrawable(holder.binding.root.context, R.drawable.a_20))
            in 21..30 -> holder.binding.indexImage.setImageDrawable(ContextCompat.getDrawable(holder.binding.root.context, R.drawable.a_30))
            in 31..40 -> holder.binding.indexImage.setImageDrawable(ContextCompat.getDrawable(holder.binding.root.context, R.drawable.a_40))
            in 41..50 -> holder.binding.indexImage.setImageDrawable(ContextCompat.getDrawable(holder.binding.root.context, R.drawable.a_50))
            in 51..60 -> holder.binding.indexImage.setImageDrawable(ContextCompat.getDrawable(holder.binding.root.context, R.drawable.a_60))
            in 61..70 -> holder.binding.indexImage.setImageDrawable(ContextCompat.getDrawable(holder.binding.root.context, R.drawable.a_70))
            in 71..80 -> holder.binding.indexImage.setImageDrawable(ContextCompat.getDrawable(holder.binding.root.context, R.drawable.a_80))
            in 81..90 -> holder.binding.indexImage.setImageDrawable(ContextCompat.getDrawable(holder.binding.root.context, R.drawable.a_90))
            else -> holder.binding.indexImage.setImageDrawable(ContextCompat.getDrawable(holder.binding.root.context, R.drawable.a_100))
        }

        holder.binding.rowConstraint.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToUpdateFragment(currentItem)
            it.findNavController().navigate(action)
        }

        holder.binding.rowConstraint.setOnLongClickListener {
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
            true
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