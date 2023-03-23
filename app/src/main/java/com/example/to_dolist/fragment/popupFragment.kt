package com.example.to_dolist.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.to_dolist.R
import com.example.to_dolist.databinding.FragmentPopupBinding
import com.example.to_dolist.utils.Tododata
import com.google.android.material.textfield.TextInputEditText


class popupFragment : DialogFragment() {
  private lateinit var binding: FragmentPopupBinding
  private  lateinit var listner:DialogueNextBtnclickListner
  private  var tododata:Tododata? =null


  fun setlistner(listner: DialogueNextBtnclickListner){
      this.listner=listner
  }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentPopupBinding.inflate(inflater,container,false)
        return binding.root
//     binding= FragmentPopupBinding.inflate(inflater,container,false)
//        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments!=null){
            tododata= Tododata(  arguments?.getString("taskId").toString(),
                arguments?.getString("task").toString())

        binding.textet.setText(tododata?.task)}
        registerevent()
    }

    private fun registerevent() {
        binding.todonxt.setOnClickListener {
            val value=binding.textet.text.toString()
            if(value.isNotEmpty()){
                if(tododata==null){
                    listner.onSavetask(value,binding.textet)
                }else{
                    tododata?.task=value
                    listner.onUodatetask(tododata!!, binding.textet)
                }

            }else{
                Toast.makeText(context,"Please Enter Task",Toast.LENGTH_SHORT).show()
            }

        }

        binding.todoclose.setOnClickListener {
            dismiss()
        }
    }

    interface DialogueNextBtnclickListner {
        fun onSavetask(todo:String, textet:TextInputEditText)
        fun onUodatetask(tododata: Tododata, textet:TextInputEditText)
        
    }
    companion object{
        const val TAG="popupFragment"
        @JvmStatic
        fun newInstance(taskid:String, task:String)=popupFragment().apply {
            arguments=Bundle().apply {
                putString("taskId",taskid)
                putString("task",task)
            }
        }

    }

}