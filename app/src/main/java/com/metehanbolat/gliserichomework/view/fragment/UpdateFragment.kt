package com.metehanbolat.gliserichomework.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.metehanbolat.gliserichomework.R
import com.metehanbolat.gliserichomework.databinding.FragmentUpdateBinding
import com.metehanbolat.gliserichomework.model.CategoryModel
import com.metehanbolat.gliserichomework.model.FoodFeaturesModel
import com.metehanbolat.gliserichomework.viewmodel.CommonViewModel

class UpdateFragment : Fragment() {

    private var _binding : FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var commonViewModel : CommonViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        val view = binding.root

        auth = Firebase.auth
        firestore = Firebase.firestore
        commonViewModel = ViewModelProvider(this)[CommonViewModel::class.java]

        binding.apply {
            foodName.setText(args.currentFoodFeatures.foodName)
            glycemicIndex.setText(args.currentFoodFeatures.glycemicIndex)
            carbohydrates.setText(args.currentFoodFeatures.carbohydrates)
            calories.setText(args.currentFoodFeatures.calories)
            category.setText(args.currentFoodFeatures.category)
            favouriteControl.isChecked = args.currentFoodFeatures.favourite == 1
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.updateButton.setOnClickListener {
            updateItem()
            addCategoryToDatabase()
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

    private fun updateItem() {
        val foodName = binding.foodName.text.toString()
        val glycemicIndex = binding.glycemicIndex.text.toString()
        val carbohydrates = binding.carbohydrates.text.toString()
        val calories = binding.calories.text.toString()
        val category = binding.category.text.toString().uppercase()
        val favourite = if (binding.favouriteControl.isChecked) 1 else 0

        val data = hashMapOf(
            "id" to args.currentFoodFeatures.id,
            "name" to foodName,
            "glycemicIndex" to glycemicIndex,
            "carbohydrates" to carbohydrates,
            "calories" to calories,
            "category" to category,
            "favourite" to favourite
        )

        if (favourite == 1) {
            firestore.collection(auth.currentUser?.email!!).document("favourite").set(hashMapOf("favourite" to "1"))
            firestore.collection(auth.currentUser?.email!!).document("favourite").collection("favourite").document(args.currentFoodFeatures.id.toString()).set(data)
        }else{
            firestore.collection(auth.currentUser?.email!!).document("favourite").collection("favourite").document(args.currentFoodFeatures.id.toString()).delete()
        }

        val updatedFoodFeatures = FoodFeaturesModel(args.currentFoodFeatures.id, foodName, glycemicIndex, carbohydrates, calories, category, favourite)
        commonViewModel.updateFoodFeatures(updatedFoodFeatures)
        findNavController().navigate(R.id.action_updateFragment_to_mainFragment)
    }

    private fun addCategoryToDatabase() {
        val category = binding.category.text.toString()
        val categoryModel = CategoryModel(category, 999)
        commonViewModel.addCategory(categoryModel)
    }

}