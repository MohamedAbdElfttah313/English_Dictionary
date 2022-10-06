package com.example.EnglishDictionary.UI.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.EnglishDictionary.R
import com.example.EnglishDictionary.Subsense
import kotlinx.android.synthetic.main.infalted_layout.view.*


class DictionaryRecViewAdapter(val dataSource : List<Subsense?>?) : RecyclerView.Adapter<DictionaryRecViewAdapter.myViewHolder>() {

    class myViewHolder(val view : View) : RecyclerView.ViewHolder(view){
        val def = view.defTextView
        val realDef = view.RealDefTextView
        val exa = view.examTextView
        val realExa  = view.RealexamTextView
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.infalted_layout, parent , false)
        return myViewHolder(view)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.def.text = "Definition : "
        holder.exa.text = "Example : "
        try {
            val definition = dataSource!![position]!!.definitions.toString()
            holder.realDef.text = definition.substring(1,definition.lastIndex)
        }catch (e:Exception){

        }


        try {
            holder.realExa.text = dataSource!![position]!!.examples!![0]!!.text
        }catch (e:Exception){

        }



    }

    override fun getItemCount(): Int {
        try {
            return dataSource!!.size
        }catch (e:Exception){
            println(e.message)
            println("No Data to show")
            return 0
        }
    }
}