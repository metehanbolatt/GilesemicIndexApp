package com.metehanbolat.gliserichomework.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.metehanbolat.gliserichomework.databinding.CategoryRowBinding
import com.metehanbolat.gliserichomework.model.CategoryModel
import com.metehanbolat.gliserichomework.model.CategoryWithFoodFeatures
import com.metehanbolat.gliserichomework.viewmodel.CommonViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryRecyclerAdapter(private val categoryList: List<CategoryModel>, private val viewModel: CommonViewModel): RecyclerView.Adapter<CategoryRecyclerAdapter.CategoryViewHolder>() {
    class CategoryViewHolder(val binding: CategoryRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = CategoryRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val currentItem = categoryList[position]
        holder.binding.category.text = currentItem.category

        holder.binding.category.setOnClickListener {
            viewModel.viewModelScope.launch(Dispatchers.Main) {
                viewModel.categoryControl.value = currentItem.category
            }
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}