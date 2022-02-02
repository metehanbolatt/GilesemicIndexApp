package com.metehanbolat.gliserichomework.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.metehanbolat.gliserichomework.adapter.FavouriteRecyclerAdapter
import com.metehanbolat.gliserichomework.databinding.FragmentFavouriteBinding
import com.metehanbolat.gliserichomework.model.FoodFeaturesModel
import com.metehanbolat.gliserichomework.viewmodel.CommonViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteFragment : Fragment() {

    private var _binding : FragmentFavouriteBinding? = null
    private val binding get() = _binding!!

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
            binding.lottie.visibility = View.GONE
            adapter = FavouriteRecyclerAdapter(favouriteList, viewModel, firebase)
            binding.favouriteRecyclerView.adapter = adapter
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}