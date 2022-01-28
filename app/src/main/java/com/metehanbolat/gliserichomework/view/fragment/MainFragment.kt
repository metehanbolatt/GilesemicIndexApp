package com.metehanbolat.gliserichomework.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.metehanbolat.gliserichomework.databinding.FragmentMainBinding
import com.metehanbolat.gliserichomework.model.FoodFeatures
import com.metehanbolat.gliserichomework.utils.Constants.URL
import com.metehanbolat.gliserichomework.viewmodel.MainFragmentViewModel
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import java.util.*
import kotlin.collections.ArrayList

class MainFragment : Fragment() {

    private var _binding : FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel : MainFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(this)[MainFragmentViewModel::class.java]

        viewModel.viewModelScope.launch(Dispatchers.IO) {
            val document = Jsoup.connect(URL).get()
            viewModel.getData(document)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.firstDataList.observe(viewLifecycleOwner){
            if (!it.isNullOrEmpty()){
                val tableList = ArrayList<String>()
                val featuresList = listOf("Besin maddesi", "Glisemik indeks", "Karbonhidrat miktarı (100 g'daki)", "Kalori (100 g'daki)")
                val dataList = it
                for (i in 0 until dataList.size - 3){
                    //println(dataList[i])
                    for ((index, a) in dataList[i].withIndex()){
                        if (a != ' '){
                            if (a.isLowerCase() || a.isDigit()){
                                break
                            }else {
                                if (index == dataList[i].length - 1){
                                    tableList.add(dataList[i])
                                }
                            }
                        }
                    }
                }
                // Data List Başlıktan arındırıldı.
                dataList.removeAll(tableList)
                // Data List Featurestan arındırıldı.
                dataList.removeAll(featuresList)
                // En sondaki saçma şeyler temizlendi.
                for (i in 1..3){
                    dataList.removeAt(dataList.size - 1)
                }
                // Datalar saf halde
                //println(dataList)

                val foodNameList = ArrayList<String>()
                val glycemicIndexList = ArrayList<String>()
                val carbohydratesList = ArrayList<String>()
                val caloriesList = ArrayList<String>()
                // Yemeklerin özelliklerini ayrı ayrı listelere aldım.
                for ((index, data) in dataList.withIndex()){
                    when(index % 4){
                        0 -> foodNameList.add(data)
                        1 -> glycemicIndexList.add(data)
                        2 -> carbohydratesList.add(data)
                        3 -> caloriesList.add(data)
                    }
                }
                val foodFeaturesList = ArrayList<FoodFeatures>()
                for (i in 0 until foodNameList.size){
                    val foodFeatures = FoodFeatures(foodNameList[i], glycemicIndexList[i], carbohydratesList[i], caloriesList[i])
                    foodFeaturesList.add(foodFeatures)
                }
                println(foodFeaturesList)

            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}