package com.example.to_dolist.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.to_dolist.R
import com.example.to_dolist.databinding.FragmentSignInBinding
import com.example.to_dolist.databinding.FragmentSignUpBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth


class SignInFragment : Fragment() {
    private  lateinit var auth: FirebaseAuth
    private lateinit var navControl: NavController
    private lateinit var binding: FragmentSignInBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSignInBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        registerEvents()
    }

    private fun registerEvents() {
        binding.authtext.setOnClickListener{
            navControl.navigate(R.id.action_signInFragment_to_sign_UpFragment3)
        }
        binding.signinbtn.setOnClickListener {
            val email = binding.emailet.toString().trim()
            val password  = binding.passet.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()){

                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                        OnCompleteListener {
                            if (it.isSuccessful){
                            Toast.makeText(context,"Login Successful !!", Toast.LENGTH_SHORT).show()
                            navControl.navigate(R.id.action_signInFragment_to_homeFragment)}
                            else{
                                Toast.makeText(context,"Credentials Not Found!!", Toast.LENGTH_SHORT).show()

                            }
                        })


            }
        }
    }

    private fun init(view: View) {
        navControl= Navigation.findNavController(view)
        auth=FirebaseAuth.getInstance()



    }

}