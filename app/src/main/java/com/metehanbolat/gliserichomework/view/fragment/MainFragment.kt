package com.metehanbolat.gliserichomework.view.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.metehanbolat.gliserichomework.R
import com.metehanbolat.gliserichomework.adapter.FoodFeaturesRecyclerAdapter
import com.metehanbolat.gliserichomework.databinding.FragmentMainBinding
import com.metehanbolat.gliserichomework.roommodel.FoodFeaturesModel
import com.metehanbolat.gliserichomework.roommodel.CategoryModel
import com.metehanbolat.gliserichomework.utils.Constants.URL
import com.metehanbolat.gliserichomework.viewmodel.MainFragmentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class MainFragment : Fragment() {

    private var _binding : FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainFragmentViewModel : MainFragmentViewModel
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root

        mainFragmentViewModel = ViewModelProvider(this)[MainFragmentViewModel::class.java]

        sharedPreferences = this.requireActivity().getSharedPreferences("com.metehanbolat.gliserichomework", Context.MODE_PRIVATE)

        mainFragmentViewModel.viewModelScope.launch(Dispatchers.IO) {
            val control = sharedPreferences.getInt("control", 0)
            if (control == 0) {
                val document = Jsoup.connect(URL).get()
                mainFragmentViewModel.getData(document)
                // aşağıdakini 0 dan farklı bir değer yaparsan verileri sadece bir kere çeker.
                // Burdan sonra database almalısın.
                withContext(Dispatchers.Main) {
                    mainFragmentViewModel.firstDataList.observe(viewLifecycleOwner) {
                        if (!it.isNullOrEmpty()) {
                            val tableList = ArrayList<String>()
                            val featuresList = listOf(
                                "Besin maddesi",
                                "Glisemik indeks",
                                "Karbonhidrat miktarı (100 g'daki)",
                                "Kalori (100 g'daki)"
                            )
                            val dataList = it
                            // Sondaki saçma şeyler silindi.
                            for (i in 1..3) {
                                dataList.removeAt(dataList.size - 1)
                            }
                            val categoryList = ArrayList<CategoryModel>()
                            for (i in 0 until dataList.size) {
                                for ((index, a) in dataList[i].withIndex()) {
                                    if (a != ' ') {
                                        if (a.isLowerCase() || a.isDigit()) {
                                            break
                                        } else {
                                            if (index == dataList[i].length - 1) {
                                                tableList.add(dataList[i])
                                                categoryList.add(CategoryModel(dataList[i], i))
                                                mainFragmentViewModel.addTitle(CategoryModel(dataList[i], i))
                                            }
                                        }
                                    }
                                }
                            }
                            // Data List Başlıktan arındırıldı.
                            dataList.removeAll(tableList)
                            // Data List Featurestan arındırıldı.
                            dataList.removeAll(featuresList)

                            // Datalar saf halde
                            println(dataList)
                            for (i in 0 until categoryList.size) {
                                println("${categoryList[i].category}: ${categoryList[i].index}")
                            }
                            val foodNameList = ArrayList<String>()
                            val glycemicIndexList = ArrayList<String>()
                            val carbohydratesList = ArrayList<String>()
                            val caloriesList = ArrayList<String>()
                            // Yemeklerin özelliklerini ayrı ayrı listelere aldım.
                            for ((index, data) in dataList.withIndex()) {
                                when (index % 4) {
                                    0 -> foodNameList.add(data)
                                    1 -> glycemicIndexList.add(data)
                                    2 -> carbohydratesList.add(data)
                                    3 -> caloriesList.add(data)
                                }
                            }
                            val foodFeaturesList = ArrayList<FoodFeaturesModel>()
                            for (i in 0 until foodNameList.size) {
                                // 0 dan 46'ya toplam 47 data
                                if (i < ((((categoryList[1].index) - 1) / 4) - 1)) {
                                    val foodFeatures = FoodFeaturesModel(
                                        0,
                                        foodNameList[i],
                                        glycemicIndexList[i],
                                        carbohydratesList[i],
                                        caloriesList[i],
                                        categoryList[0].category
                                    )
                                    foodFeaturesList.add(foodFeatures)
                                // 47 den 57'ye toplam 11 data
                                }else if (i < ((((categoryList[2].index) - 2) / 4) - 2)) {
                                    val foodFeatures = FoodFeaturesModel(
                                        0,
                                        foodNameList[i],
                                        glycemicIndexList[i],
                                        carbohydratesList[i],
                                        caloriesList[i],
                                        categoryList[1].category
                                    )
                                    foodFeaturesList.add(foodFeatures)
                                // 58'den 82'ye toplam 25 data
                                }else if(i < ((((categoryList[3].index) - 3) / 4) - 3)) {
                                    val foodFeatures = FoodFeaturesModel(
                                        0,
                                        foodNameList[i],
                                        glycemicIndexList[i],
                                        carbohydratesList[i],
                                        caloriesList[i],
                                        categoryList[2].category
                                    )
                                    foodFeaturesList.add(foodFeatures)
                                // 83'ten 94'e toplam 12 data
                                }else if (i < ((((categoryList[4].index) - 4) / 4) - 4)) {
                                    val foodFeatures = FoodFeaturesModel(
                                        0,
                                        foodNameList[i],
                                        glycemicIndexList[i],
                                        carbohydratesList[i],
                                        caloriesList[i],
                                        categoryList[3].category
                                    )
                                    foodFeaturesList.add(foodFeatures)
                                // 95'ten 98'e kadar toplam 4 data
                                }else if (i < ((((categoryList[5].index) - 5) / 4) - 5)) {
                                    val foodFeatures = FoodFeaturesModel(
                                        0,
                                        foodNameList[i],
                                        glycemicIndexList[i],
                                        carbohydratesList[i],
                                        caloriesList[i],
                                        categoryList[4].category
                                    )
                                    foodFeaturesList.add(foodFeatures)
                                }else{
                                    val foodFeatures = FoodFeaturesModel(
                                        0,
                                        foodNameList[i],
                                        glycemicIndexList[i],
                                        carbohydratesList[i],
                                        caloriesList[i],
                                        categoryList[5].category
                                    )
                                    foodFeaturesList.add(foodFeatures)
                                }
                            }
                            // RoomDatabase'e ekleme işlemi
                            foodFeaturesList.forEach { foodFeatures ->
                                mainFragmentViewModel.addFoodFeatures(foodFeatures)
                            }
                        }
                    }
                }
                sharedPreferences.edit().putInt("control", 1).apply()
            }
        }

        val foodFeaturesAdapter = FoodFeaturesRecyclerAdapter()
        val recyclerView = binding.recyclerView
        recyclerView.adapter = foodFeaturesAdapter

        mainFragmentViewModel.readAllData.observe(viewLifecycleOwner) { foodFeatures ->
            foodFeaturesAdapter.setData(foodFeatures)
        }

        mainFragmentViewModel.viewModelScope.launch(Dispatchers.IO) {
            println("categorili ${mainFragmentViewModel.getCategoryWithData("ŞEKERLİ GIDALAR")}")
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_addUpdateFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}