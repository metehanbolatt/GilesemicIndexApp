package com.metehanbolat.gliserichomework.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.metehanbolat.gliserichomework.databinding.FragmentProfileBinding
import com.metehanbolat.gliserichomework.viewmodel.ProfileFragmentViewModel

class ProfileFragment : Fragment() {

    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var profileFragmentViewModel: ProfileFragmentViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        profileFragmentViewModel = ViewModelProvider(this)[ProfileFragmentViewModel::class.java]
        auth = Firebase.auth
        firestore = Firebase.firestore

        profileFragmentViewModel.getUserDataFromFirebase(firestore, auth)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.exitButton.setOnClickListener {
            profileFragmentViewModel.exitApp(requireActivity(), auth)
        }

        profileFragmentViewModel.userData.observe(viewLifecycleOwner) { userModel ->
            binding.apply {
                userName.text = userModel.name
                userSurname.text = userModel.surname
                userPhone.text = userModel.phone
                userEmail.text = userModel.email
                userPassword.text = userModel.password
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}