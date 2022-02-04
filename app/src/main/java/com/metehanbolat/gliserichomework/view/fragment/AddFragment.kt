package com.metehanbolat.gliserichomework.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.metehanbolat.gliserichomework.R
import com.metehanbolat.gliserichomework.databinding.FragmentAddBinding
import com.metehanbolat.gliserichomework.model.CategoryModel
import com.metehanbolat.gliserichomework.model.FoodFeaturesModel
import com.metehanbolat.gliserichomework.viewmodel.CommonViewModel

class AddFragment : Fragment() {

    private var _binding : FragmentAddBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    private lateinit var commonViewModel: CommonViewModel
    private var lastIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val view = binding.root

        auth = Firebase.auth
        firestore = Firebase.firestore
        commonViewModel = ViewModelProvider(this)[CommonViewModel::class.java]

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addButton.setOnClickListener {
            when {
                binding.foodName.text.isBlank() -> Snackbar.make(it, "Food Name must be filled.", Snackbar.LENGTH_SHORT).show()
                binding.glycemicIndex.text.isBlank() -> Snackbar.make(it, "Glycemic Index must be filled.", Snackbar.LENGTH_SHORT).show()
                binding.carbohydrates.text.isBlank() -> Snackbar.make(it, "Carbohydrate must be filled.", Snackbar.LENGTH_SHORT).show()
                binding.calories.text.isBlank() -> Snackbar.make(it, "Calorie must be filled.", Snackbar.LENGTH_SHORT).show()
                binding.category.text.isBlank() -> Snackbar.make(it, "Category must be filled.", Snackbar.LENGTH_SHORT).show()
                else -> {
                    insertDataToDatabase()
                    insertCategoryToDatabase()
                }
            }
        }

        binding.exitButton.setOnClickListener {
            findNavController().navigate(R.id.action_addUpdateFragment_to_mainFragment)
        }

        binding.glycemicIndex.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                val index = p0.toString()
                if (index == ""){
                    glycemicImageEdit(0)
                }else{
                    glycemicImageEdit(p0.toString().toInt())
                }
            }
        })

        commonViewModel.readAllCategory.observe(viewLifecycleOwner) { categoryModelList ->
            val categoryList = arrayListOf<String>()
            categoryModelList.forEach { categoryModel ->
                categoryList.add(categoryModel.category)
            }
            val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, categoryList)
            binding.category.setAdapter(arrayAdapter)
        }

        commonViewModel.readAllData.observe(viewLifecycleOwner) {
            lastIndex = it.size + 1
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
        val category = binding.category.text.toString().uppercase()
        val favourite = if (binding.favouriteControl.isChecked) 1 else 0

        val foodFeatures = FoodFeaturesModel(0, foodName, glycemicIndex, carbohydrates, calories, category, favourite)
        commonViewModel.addFoodFeatures(foodFeatures)
        val data = hashMapOf(
            "id" to lastIndex,
            "name" to foodName,
            "glycemicIndex" to glycemicIndex,
            "carbohydrates" to carbohydrates,
            "calories" to calories,
            "category" to category,
            "favourite" to favourite
        )
        if (favourite == 1) {
            firestore.collection(auth.currentUser?.email!!).document("favourite").set(hashMapOf("favourite" to "1"))
            firestore.collection(auth.currentUser?.email!!).document("favourite").collection("favourite").document(lastIndex.toString()).set(data)
        }

        Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_addUpdateFragment_to_mainFragment)
    }

    private fun insertCategoryToDatabase() {
        val category = binding.category.text.toString().uppercase()
        val categoryModel = CategoryModel(category, 999)
        commonViewModel.addCategory(categoryModel)
    }

    private fun glycemicImageEdit(glycemicIndex: Int) {
        when(glycemicIndex) {
            0 -> binding.glycemicIndexImage.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.a_0))
            in 1..10 -> binding.glycemicIndexImage.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.a_10))
            in 11..20 -> binding.glycemicIndexImage.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.a_20))
            in 21..30 -> binding.glycemicIndexImage.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.a_30))
            in 31..40 -> binding.glycemicIndexImage.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.a_40))
            in 41..50 -> binding.glycemicIndexImage.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.a_50))
            in 51..60 -> binding.glycemicIndexImage.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.a_60))
            in 61..70 -> binding.glycemicIndexImage.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.a_70))
            in 71..80 -> binding.glycemicIndexImage.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.a_80))
            in 81..90 -> binding.glycemicIndexImage.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.a_90))
            else -> binding.glycemicIndexImage.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.a_100))
        }
    }

}