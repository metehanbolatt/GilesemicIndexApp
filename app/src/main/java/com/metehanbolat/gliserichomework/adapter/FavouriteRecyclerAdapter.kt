package com.metehanbolat.gliserichomework.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.metehanbolat.gliserichomework.R
import com.metehanbolat.gliserichomework.databinding.FavouriteRowBinding
import com.metehanbolat.gliserichomework.model.FoodFeaturesModel
import com.metehanbolat.gliserichomework.view.fragment.FavouriteFragmentDirections
import com.metehanbolat.gliserichomework.viewmodel.CommonViewModel

class FavouriteRecyclerAdapter(private val favouriteList: ArrayList<FoodFeaturesModel>, private val commonViewModel: CommonViewModel, private var firebase: Firebase): RecyclerView.Adapter<FavouriteRecyclerAdapter.FavouriteViewHolder>() {
    class FavouriteViewHolder(val binding: FavouriteRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val binding = FavouriteRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavouriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val currentItem = favouriteList[position]
        val auth = firebase.auth
        val firestore = firebase.firestore
        val currentUser = auth.currentUser?.email!!
        holder.binding.foodName.text = currentItem.foodName
        holder.binding.glycemicIndex.text = currentItem.glycemicIndex
        holder.binding.carbohydrate.text = currentItem.carbohydrates
        holder.binding.calorie.text = currentItem.calories


        holder.binding.favouriteImage.setOnClickListener {
            firestore.collection(currentUser).document("favourite").collection("favourite").document(currentItem.id.toString()).delete()
            commonViewModel.updateFoodFeatures(FoodFeaturesModel(currentItem.id, currentItem.foodName, currentItem.glycemicIndex, currentItem.carbohydrates, currentItem.calories, currentItem.category, 0))
            favouriteList.removeAt(position)
            notifyDataSetChanged()
            if (favouriteList.isNullOrEmpty()){
                commonViewModel.emptyListControl.value = true
            }
        }

        holder.binding.rowConstraint.setOnClickListener {
            val action = FavouriteFragmentDirections.actionFavouriteFragmentToUpdateFragment(currentItem)
            it.findNavController().navigate(action)
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
    }

    override fun getItemCount(): Int {
        return favouriteList.size
    }
}