package com.metehanbolat.gliserichomework.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.metehanbolat.gliserichomework.R
import com.metehanbolat.gliserichomework.databinding.FragmentAddBinding
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

        val foodFeatures = FoodFeaturesModel(0, foodName, glycemicIndex, carbohydrates, calories, "deneme")
        commonViewModel.addFoodFeatures(foodFeatures)
        Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_addUpdateFragment_to_mainFragment)
    }

}