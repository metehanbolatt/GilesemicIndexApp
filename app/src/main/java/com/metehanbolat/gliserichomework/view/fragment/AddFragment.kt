package com.metehanbolat.gliserichomework.view.fragment

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.metehanbolat.gliserichomework.R
import com.metehanbolat.gliserichomework.databinding.FragmentAddBinding
import com.metehanbolat.gliserichomework.model.CategoryModel
import com.metehanbolat.gliserichomework.model.FoodFeaturesModel
import com.metehanbolat.gliserichomework.viewmodel.CommonViewModel

class AddFragment : Fragment() {

    private var _binding : FragmentAddBinding? = null
    private val binding get() = _binding!!

    private lateinit var commonViewModel: CommonViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val view = binding.root

        commonViewModel = ViewModelProvider(this)[CommonViewModel::class.java]

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addButton.setOnClickListener {
            insertDataToDatabase()
            insertCategoryToDatabase()
        }

        commonViewModel.readAllCategory.observe(viewLifecycleOwner) { categoryModelList ->
            val categoryList = arrayListOf<String>()
            categoryModelList.forEach { categoryModel ->
                categoryList.add(categoryModel.category)
            }
            val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, categoryList)
            binding.category.setAdapter(arrayAdapter)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun insertDataToDatabase() {
        val foodName = binding.foodName.text.toString()
        val glycemicIndex = binding.glycemicIndex.text.toString()
        val carbohydrates = binding.carbohydrates.text.toString()
        val calories = binding.calories.text.toString()
        val category = binding.category.text.toString()

        val foodFeatures = FoodFeaturesModel(0, foodName, glycemicIndex, carbohydrates, calories, category)
        commonViewModel.addFoodFeatures(foodFeatures)
        Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_addUpdateFragment_to_mainFragment)
    }

    private fun insertCategoryToDatabase() {
        val category = binding.category.text.toString()
        val categoryModel = CategoryModel(category, 999)
        commonViewModel.addCategory(categoryModel)
    }

}