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
import com.metehanbolat.gliserichomework.databinding.FragmentAddUpdateBinding
import com.metehanbolat.gliserichomework.roomdatabase.FoodFeaturesModel
import com.metehanbolat.gliserichomework.roomdatabase.FoodFeaturesViewModel
import com.metehanbolat.gliserichomework.viewmodel.MainFragmentViewModel

class AddUpdateFragment : Fragment() {

    private var _binding : FragmentAddUpdateBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainFragmentViewModel: MainFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddUpdateBinding.inflate(inflater, container, false)
        val view = binding.root

        mainFragmentViewModel = ViewModelProvider(this)[MainFragmentViewModel::class.java]

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.foodButton.setOnClickListener {
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

        val foodFeatures = FoodFeaturesModel(0, foodName, glycemicIndex, carbohydrates, calories)
        mainFragmentViewModel.addFoodFeatures(foodFeatures)
        Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_addUpdateFragment_to_mainFragment)
    }

}