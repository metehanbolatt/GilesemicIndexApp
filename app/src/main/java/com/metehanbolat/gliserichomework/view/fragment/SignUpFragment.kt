package com.metehanbolat.gliserichomework.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.ktx.Firebase
import com.metehanbolat.gliserichomework.databinding.FragmentSignUpBinding
import com.metehanbolat.gliserichomework.viewmodel.SignUpFragmentViewModel

class SignUpFragment : Fragment() {

    private var _binding : FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private lateinit var signUpFragmentViewModel: SignUpFragmentViewModel
    private lateinit var firebase: Firebase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val view = binding.root

        signUpFragmentViewModel = ViewModelProvider(this)[SignUpFragmentViewModel::class.java]
        firebase = Firebase

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUpButton.setOnClickListener {
            when {
                binding.name.text.toString().isBlank() -> Snackbar.make(it, "Name is empty!! Please fill.", Snackbar.LENGTH_SHORT).show()
                binding.surname.text.toString().isBlank() -> Snackbar.make(it, "Surname is empty!! Please fill.", Snackbar.LENGTH_SHORT).show()
                binding.phone.text.toString().isBlank() -> Snackbar.make(it, "Phone is empty!! Please fill.", Snackbar.LENGTH_SHORT).show()
                binding.email.text.toString().isBlank() -> Snackbar.make(it, "Email is empty!! Please fill.", Snackbar.LENGTH_SHORT).show()
                binding.password.text.toString().isBlank() -> Snackbar.make(it, "Password is empty!! Please fill.", Snackbar.LENGTH_SHORT).show()
                else -> {
                    signUpFragmentViewModel.createUser(requireActivity(), it, firebase, binding.name.text.toString(), binding.surname.text.toString(), binding.phone.text.toString(), binding.email.text.toString(), binding.password.text.toString())

                }
            }
        }

        signUpFragmentViewModel.loadingControl.observe(viewLifecycleOwner) {
            loadingControl(it)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadingControl(control: Boolean) {
        if (control) {
            binding.signUpConstraint.visibility = View.INVISIBLE
            binding.lottie.visibility = View.VISIBLE
            binding.lottie.playAnimation()
        }else{
            binding.signUpConstraint.visibility = View.VISIBLE
            binding.lottie.pauseAnimation()
            binding.lottie.visibility = View.INVISIBLE
        }
    }

}