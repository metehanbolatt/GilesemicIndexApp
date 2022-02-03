package com.metehanbolat.gliserichomework.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
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
            when {
                binding.email.text.isBlank() -> {
                    Snackbar.make(it, "Email must be filled.", Snackbar.LENGTH_SHORT).show()
                }
                binding.password.text.isBlank() -> {
                    Snackbar.make(it, "Password must be filled.", Snackbar.LENGTH_SHORT).show()
                }
                else -> {
                    loginFragmentViewModel.logInFirebase(requireActivity(), it, binding.email.text.toString(), binding.password.text.toString(), firebase)
                }
            }
        }

        loginFragmentViewModel.loadingControl.observe(viewLifecycleOwner) {
            loadingControl(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadingControl(control: Boolean) {
        if (control) {
            binding.logInConstraint.visibility = View.INVISIBLE
            binding.lottie.visibility = View.VISIBLE
            binding.lottie.playAnimation()
        }else{
            binding.logInConstraint.visibility = View.VISIBLE
            binding.lottie.pauseAnimation()
            binding.lottie.visibility = View.INVISIBLE
        }
    }

}