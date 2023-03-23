package com.example.to_dolist.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.to_dolist.databinding.FragmentHomeBinding
import com.example.to_dolist.utils.Adapter
import com.example.to_dolist.utils.Tododata
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class HomeFragment : Fragment(), popupFragment.DialogueNextBtnclickListner,
    Adapter.Todoadapterclickinterface {
    private lateinit var auth:FirebaseAuth
    private  lateinit var databaseRef: DatabaseReference
    private  lateinit var navControl: NavController
    private lateinit var binding: FragmentHomeBinding
    private   var popup:popupFragment?=null
    private lateinit var adapter: com.example.to_dolist.utils.Adapter
    private lateinit var mlist: MutableList<Tododata>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        getdatafromfireabase()
        registerview()
    }

    private fun getdatafromfireabase() {
       databaseRef.addValueEventListener(object :ValueEventListener{
           override fun onDataChange(snapshot: DataSnapshot) {
              mlist.clear()
               for (takesnapshot in snapshot.children){
                   val todotask=takesnapshot.key?.let{
                       Tododata(it,takesnapshot.value.toString())
                   }
                   if (todotask!=null){
                       mlist.add(todotask)
                   }
               }
               adapter.notifyDataSetChanged()
           }

           override fun onCancelled(error: DatabaseError) {
               Toast.makeText(context,error.message,Toast.LENGTH_SHORT).show()
           }


       })
    }

    private fun registerview() {
        binding.addbtn.setOnClickListener {
            if (popupFragment!=null)
                childFragmentManager.beginTransaction().remove(popup!!).commit()

            popup = popupFragment()
            popup!!.setlistner(this)
            popup!!.show(
                childFragmentManager, popupFragment.TAG
            )
        }
    }


    private fun init(view: View) {
        navControl= Navigation.findNavController(view)
        auth= FirebaseAuth.getInstance()
        databaseRef=FirebaseDatabase.getInstance().reference
            .child("Tasks").child(auth.currentUser?.uid.toString())

        binding.rv.setHasFixedSize(true)
        binding.rv.layoutManager=LinearLayoutManager(context)
        mlist= mutableListOf()
         adapter=com.example.to_dolist.utils.Adapter(mlist)
        adapter.setlistner(this)
        binding.rv.adapter=adapter

    }

    override fun onSavetask(todo: String, textet: TextInputEditText) {
  databaseRef.push().setValue(todo).addOnCompleteListener {
      if(it.isSuccessful){
          Toast.makeText(context,"Task saved !!",Toast.LENGTH_SHORT).show()
          textet.text=null
      }else{
          Toast.makeText(context,"Task not saved",Toast.LENGTH_SHORT).show()
      }
  }
        popup!!.dismiss()
    }

    override fun onUodatetask(tododata: Tododata, textet: TextInputEditText) {
       val map=HashMap<String,Any>()
        map[tododata.taskid]=tododata.task
        databaseRef.updateChildren(map).addOnCompleteListener {
            if (it.isSuccessful){
                Toast.makeText(context,"Updated !!",Toast.LENGTH_SHORT).show()

            }else{
                Toast.makeText(context,"Unable to update",Toast.LENGTH_SHORT).show()
            }
            textet.text=null
            popup!!.dismiss()
        }
    }

    override fun ondeletebtnclick(tododata: Tododata) {
        databaseRef.child(tododata.taskid).removeValue().addOnCompleteListener {
            if (it.isSuccessful){
                Toast.makeText(context,"Task deleted",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context,"Unable to delete task",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun oneditbtnclick(tododata: Tododata) {
   if (popup!=null){
       childFragmentManager.beginTransaction().remove(popup!!).commit()
   }
        popup= popupFragment.newInstance(tododata.taskid,tododata.task)
        popup!!.setlistner(this)
        popup!!.show(childFragmentManager,popupFragment.TAG)
    }

}