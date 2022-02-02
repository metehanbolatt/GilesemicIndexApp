package com.metehanbolat.gliserichomework.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.metehanbolat.gliserichomework.databinding.FragmentFavouriteBinding
import com.metehanbolat.gliserichomework.viewmodel.CommonViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteFragment : Fragment() {

    private var _binding : FragmentFavouriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CommonViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(this)[CommonViewModel::class.java]

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.viewModelScope.launch(Dispatchers.IO) {
            println(viewModel.getFavouriteFoodFeatures())
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}