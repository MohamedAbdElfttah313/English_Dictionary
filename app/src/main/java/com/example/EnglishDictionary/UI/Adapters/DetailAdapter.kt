package com.example.EnglishDictionary.UI.Adapters



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.EnglishDictionary.OtherDefsAndExam
import com.example.EnglishDictionary.R
import kotlinx.android.synthetic.main.inflated_layout_for_recent.view.*


class DetailAdapter (val dataSource : List<OtherDefsAndExam>) : RecyclerView.Adapter<DetailAdapter.myViewHolder3>() {

    class myViewHolder3(val view : View) : RecyclerView.ViewHolder(view){
        val realDef = view.RealDefTextView2
        val realExa  = view.RealexamTextView2
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder3 {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.inflated_layout_for_recent, parent , false)
        return myViewHolder3(view)
    }

    override fun onBindViewHolder(holder: myViewHolder3, position: Int) {
        holder.realDef.text = dataSource[position].OtherDefs
        holder.realExa.text = dataSource[position].Other_Exa

    }

    override fun getItemCount(): Int {
        return dataSource.size
    }


}