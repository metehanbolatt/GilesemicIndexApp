package com.metehanbolat.gliserichomework.view.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.metehanbolat.gliserichomework.R
import com.metehanbolat.gliserichomework.adapter.CategoryRecyclerAdapter
import com.metehanbolat.gliserichomework.adapter.FoodFeaturesRecyclerAdapter
import com.metehanbolat.gliserichomework.databinding.FragmentMainBinding
import com.metehanbolat.gliserichomework.model.FoodFeaturesModel
import com.metehanbolat.gliserichomework.model.CategoryModel
import com.metehanbolat.gliserichomework.utils.Constants.URL
import com.metehanbolat.gliserichomework.viewmodel.CommonViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class MainFragment : Fragment() {

    private var _binding : FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var commonViewModel : CommonViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var foodFeaturesAdapter: FoodFeaturesRecyclerAdapter
    private lateinit var categoryAdapter: CategoryRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root

        commonViewModel = ViewModelProvider(this)[CommonViewModel::class.java]

        sharedPreferences = this.requireActivity().getSharedPreferences("com.metehanbolat.gliserichomework", Context.MODE_PRIVATE)

        commonViewModel.viewModelScope.launch(Dispatchers.IO) {
            val control = sharedPreferences.getInt("control", 0)
            if (control == 0) {
                val document = Jsoup.connect(URL).get()
                commonViewModel.getData(document)
                // aşağıdakini 0 dan farklı bir değer yaparsan verileri sadece bir kere çeker.
                // Burdan sonra database almalısın.
                withContext(Dispatchers.Main) {
                    commonViewModel.firstDataList.observe(viewLifecycleOwner) {
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
                                                commonViewModel.addCategory(CategoryModel(dataList[i], i))
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
                            // Kategorilere ayırma işlemi
                            val foodFeaturesList = ArrayList<FoodFeaturesModel>()
                            for (i in 0 until foodNameList.size) {
                                when {
                                    i < ((((categoryList[1].index) - 1) / 4) - 1) -> {
                                        val foodFeatures = FoodFeaturesModel(
                                            0,
                                            foodNameList[i],
                                            glycemicIndexList[i],
                                            carbohydratesList[i],
                                            caloriesList[i],
                                            categoryList[0].category
                                        )
                                        foodFeaturesList.add(foodFeatures)
                                    }
                                    i < ((((categoryList[2].index) - 2) / 4) - 2) -> {
                                        val foodFeatures = FoodFeaturesModel(
                                            0,
                                            foodNameList[i],
                                            glycemicIndexList[i],
                                            carbohydratesList[i],
                                            caloriesList[i],
                                            categoryList[1].category
                                        )
                                        foodFeaturesList.add(foodFeatures)
                                    }
                                    i < ((((categoryList[3].index) - 3) / 4) - 3) -> {
                                        val foodFeatures = FoodFeaturesModel(
                                            0,
                                            foodNameList[i],
                                            glycemicIndexList[i],
                                            carbohydratesList[i],
                                            caloriesList[i],
                                            categoryList[2].category
                                        )
                                        foodFeaturesList.add(foodFeatures)
                                    }
                                    i < ((((categoryList[4].index) - 4) / 4) - 4) -> {
                                        val foodFeatures = FoodFeaturesModel(
                                            0,
                                            foodNameList[i],
                                            glycemicIndexList[i],
                                            carbohydratesList[i],
                                            caloriesList[i],
                                            categoryList[3].category
                                        )
                                        foodFeaturesList.add(foodFeatures)
                                    }
                                    i < ((((categoryList[5].index) - 5) / 4) - 5) -> {
                                        val foodFeatures = FoodFeaturesModel(
                                            0,
                                            foodNameList[i],
                                            glycemicIndexList[i],
                                            carbohydratesList[i],
                                            caloriesList[i],
                                            categoryList[4].category
                                        )
                                        foodFeaturesList.add(foodFeatures)
                                    }
                                    else -> {
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
                            }
                            // RoomDatabase'e ekleme işlemi
                            foodFeaturesList.forEach { foodFeatures ->
                                commonViewModel.addFoodFeatures(foodFeatures)
                            }
                        }
                    }
                }
                sharedPreferences.edit().putInt("control", 1).apply()
            }
        }

        foodFeaturesAdapter = FoodFeaturesRecyclerAdapter(commonViewModel)
        val recyclerView = binding.recyclerView
        recyclerView.adapter = foodFeaturesAdapter

        commonViewModel.readAllCategory.observe(viewLifecycleOwner) {
            categoryAdapter = CategoryRecyclerAdapter(it, commonViewModel)
            val categoryRecyclerView = binding.categoryRecyclerView
            categoryRecyclerView.adapter = categoryAdapter
        }

        commonViewModel.readAllData.observe(viewLifecycleOwner) { foodFeatures ->
            foodFeaturesAdapter.setData(foodFeatures)
        }

        commonViewModel.categoryControl.observe(viewLifecycleOwner){ category ->
            commonViewModel.viewModelScope.launch(Dispatchers.Main) {
               foodFeaturesAdapter.setData(commonViewModel.getCategoryWithData(category)[0].foodFeatures)
               //println("categorili ${commonViewModel.getCategoryWithData(category)}")
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_addUpdateFragment)
        }

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                searchDatabase(p0.toString())
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun searchDatabase(query: String) {
        val searchQuery = "%$query%"
        commonViewModel.searchDatabase(searchQuery).observe(this) { list ->
            list.let {
                foodFeaturesAdapter.setData(it)
            }
        }
    }
}