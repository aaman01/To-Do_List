package com.example.to_dolist.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.to_dolist.databinding.EachtodoitemBinding
import com.example.to_dolist.fragment.HomeFragment

public class Adapter (private val list: MutableList<Tododata>):
    RecyclerView.Adapter<Adapter.TpdoViewHolder>() {

    private var listener:Todoadapterclickinterface?=null

    fun setlistner(listener: HomeFragment){
        this.listener=listener
    }

    inner class TpdoViewHolder(val binding:EachtodoitemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TpdoViewHolder {
         val binding= EachtodoitemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  TpdoViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: TpdoViewHolder, position: Int) {
      with(holder){
          with(list[position]){
              binding.todotask.text=this.task
              binding.deletetask.setOnClickListener {
                  listener?.ondeletebtnclick(this)
              }
              binding.edittask.setOnClickListener {
                  listener?.oneditbtnclick(this)
              }
          }
      }
    }

    interface Todoadapterclickinterface{
  fun ondeletebtnclick(tododata:Tododata)
  fun oneditbtnclick(tododata: Tododata)
    }

}