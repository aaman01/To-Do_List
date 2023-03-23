package com.example.to_dolist.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.to_dolist.R
import com.example.to_dolist.databinding.FragmentSignUpBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth


class Sign_UpFragment : Fragment() {

   private  lateinit var auth:FirebaseAuth
   private lateinit var navControl: NavController
   private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSignUpBinding.inflate(inflater,container,false)
         return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        registerEvents()
    }

    private fun registerEvents() {
        binding.authtext.setOnClickListener{
            navControl.navigate(R.id.action_sign_UpFragment_to_signInFragment)
        }
      binding.Signupbtn.setOnClickListener {

          val email = binding.emailet.text.toString().trim()
          val password  = binding.passet.text.toString().trim()
          val verifypass= binding.verifypass.text.toString().trim()

          if (email.isNotEmpty() && password.isNotEmpty() && verifypass.isNotEmpty()){

              if(password == verifypass){

                  auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(OnCompleteListener
                  {

                      if (it.isSuccessful){

                          Toast.makeText(context,"Registered Successfully !!",Toast.LENGTH_SHORT).show()
                          navControl.navigate(R.id.action_sign_UpFragment_to_homeFragment)
                      }else{

                          Toast.makeText(context,"Try Again !!",Toast.LENGTH_SHORT).show()
                      }

                  })




              }else{
                  Toast.makeText(context,"Password not matching!!",Toast.LENGTH_SHORT).show()
              }
          }
      }
    }

    private fun init(view: View) {
        navControl=Navigation.findNavController(view)
        auth=FirebaseAuth.getInstance()



    }


}