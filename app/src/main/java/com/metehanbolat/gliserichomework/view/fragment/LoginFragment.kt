package com.metehanbolat.gliserichomework.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.ktx.Firebase
import com.metehanbolat.gliserichomework.R
import com.metehanbolat.gliserichomework.databinding.FragmentLoginBinding
import com.metehanbolat.gliserichomework.viewmodel.LoginFragmentViewModel

class LoginFragment : Fragment() {

    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebase: Firebase
    private lateinit var loginFragmentViewModel: LoginFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root

        firebase = Firebase
        loginFragmentViewModel = ViewModelProvider(this)[LoginFragmentViewModel::class.java]

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.register.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        binding.logInButton.setOnClickListener {
            loginFragmentViewModel.logInFirebase(requireActivity(), it, binding.email.text.toString(), binding.password.text.toString(), firebase)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}