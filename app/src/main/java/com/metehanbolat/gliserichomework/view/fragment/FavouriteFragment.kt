package com.metehanbolat.gliserichomework.view.fragment

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.metehanbolat.gliserichomework.adapter.FavouriteRecyclerAdapter
import com.metehanbolat.gliserichomework.databinding.BarChartDialogBinding
import com.metehanbolat.gliserichomework.databinding.FragmentFavouriteBinding
import com.metehanbolat.gliserichomework.model.FoodFeaturesModel
import com.metehanbolat.gliserichomework.utils.removeSelf
import com.metehanbolat.gliserichomework.viewmodel.CommonViewModel

class FavouriteFragment : Fragment() {

    private var _binding : FragmentFavouriteBinding? = null
    private val binding get() = _binding!!

    private var _dialogBinding : BarChartDialogBinding? = null
    private val dialogBinding get() = _dialogBinding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var firebase: Firebase
    private lateinit var adapter: FavouriteRecyclerAdapter
    private lateinit var favouriteList: ArrayList<FoodFeaturesModel>

    private lateinit var viewModel: CommonViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        _dialogBinding = BarChartDialogBinding.inflate(inflater, container, false)
        val view = binding.root
        firebase = Firebase
        auth = firebase.auth
        firestore = firebase.firestore
        viewModel = ViewModelProvider(this)[CommonViewModel::class.java]
        favouriteList = ArrayList()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentUser = auth.currentUser?.email!!

        firestore.collection(currentUser).document("favourite").collection("favourite").get().addOnSuccessListener { result ->
            for (document in result){
                val id = document["id"] as Long
                val foodName = document["name"] as String
                val glycemicIndex = document["glycemicIndex"] as String
                val carbohydrates = document["carbohydrates"] as String
                val calories = document["calories"] as String
                val category = document["category"] as String
                val favourite = document["favourite"] as Long
                val foodFeatures = FoodFeaturesModel(id.toInt(), foodName, glycemicIndex, carbohydrates, calories, category, favourite.toInt())
                favouriteList.add(foodFeatures)
            }
            viewModel.emptyListControl.value = result.isEmpty

            binding.lottie.visibility = View.GONE
            adapter = FavouriteRecyclerAdapter(favouriteList, viewModel, firebase)
            binding.favouriteRecyclerView.adapter = adapter
        }

        viewModel.emptyListControl.observe(viewLifecycleOwner) {
            if (it){
                binding.lottieNoData.playAnimation()
                binding.lottieNoData.visibility = View.VISIBLE
            }else{
                binding.lottieNoData.pauseAnimation()
                binding.lottieNoData.visibility = View.INVISIBLE
            }
        }

        binding.floatingActionButton.setOnClickListener {
            if (favouriteList.isNullOrEmpty()){
                Snackbar.make(it, "There is no data to show", Snackbar.LENGTH_SHORT).show()
            }else{
                val dialogBuilder = AlertDialog.Builder(requireContext())
                    .setView(dialogBinding.root)
                    .setCancelable(false)
                val dialog = dialogBuilder.show()
                dialogBinding.exitButton.setOnClickListener {
                    dialog.dismiss()
                    dialogBinding.root.removeSelf()
                }
                val barList = ArrayList<BarEntry>()
                val labelsName = ArrayList<String>()
                var counter = 0f
                favouriteList.forEach { foodFeaturesModel ->
                    barList.add(BarEntry(counter, foodFeaturesModel.glycemicIndex.toFloat()))
                    labelsName.add(foodFeaturesModel.foodName)
                    counter++
                }
                val barDataSet = BarDataSet(barList, "Foods")
                barDataSet.colors = ColorTemplate.createColors(ColorTemplate.MATERIAL_COLORS)
                barDataSet.valueTextColor = Color.BLACK
                barDataSet.valueTextSize = 16f

                val barData = BarData(barDataSet)
                dialogBinding.barChart.setFitBars(true)
                dialogBinding.barChart.data = barData
                dialogBinding.barChart.description.text = ""
                dialogBinding.barChart.animateY(2000)
                val xAxis = dialogBinding.barChart.xAxis
                xAxis.valueFormatter = IndexAxisValueFormatter(labelsName)
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.setDrawGridLines(false)
                xAxis.setDrawAxisLine(false)
                xAxis.labelCount = labelsName.size
                xAxis.labelRotationAngle = 270f
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _dialogBinding = null
    }

}