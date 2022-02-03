package com.metehanbolat.gliserichomework.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.metehanbolat.gliserichomework.databinding.CategoryRowBinding
import com.metehanbolat.gliserichomework.model.CategoryModel
import com.metehanbolat.gliserichomework.viewmodel.CommonViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryRecyclerAdapter(private val categoryList: ArrayList<CategoryModel>, private val viewModel: CommonViewModel): RecyclerView.Adapter<CategoryRecyclerAdapter.CategoryViewHolder>() {
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

        holder.binding.category.setOnLongClickListener {
            AlertDialog.Builder(it.context).apply {
                setPositiveButton("Delete") {_, _ ->
                    viewModel.deleteCategory(currentItem)
                    viewModel.deleteFoodFeaturesWithCategory(currentItem.category)
                    categoryList.removeAt(position)
                    notifyDataSetChanged()
                    Snackbar.make(it, "${currentItem.category} was deleted.", Snackbar.LENGTH_SHORT).show()
                }
                setNegativeButton("Cancel") {_, _ -> }
                setTitle("Delete ${currentItem.category}")
                setMessage("Are you sure you want to delete ${currentItem.category} and all food under the category?")
                create().show()
            }
            true
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}