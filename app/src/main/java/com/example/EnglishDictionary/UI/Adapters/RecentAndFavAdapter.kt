package com.example.EnglishDictionary.UI.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.EnglishDictionary.R
import kotlinx.android.synthetic.main.recent_inflated_items.view.*

class RecentAndFavAdapter (val dataSource: List<String> , val someFun : (String) -> (Unit)) : RecyclerView.Adapter<RecentAndFavAdapter.ViewHolder2>()  {

    class ViewHolder2(val view : View) : RecyclerView.ViewHolder(view){
        val word = view.recentWord

        fun setWord(aWord : String , someFun2 : (String)->(Unit)){
            view.setOnClickListener {
                someFun2(aWord)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder2 {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recent_inflated_items, parent, false)
        return ViewHolder2(view)
    }

    override fun onBindViewHolder(holder: ViewHolder2, position: Int) {
        holder.word.text = dataSource[position]
        holder.setWord(dataSource[position] , someFun)

    }

    override fun getItemCount(): Int {
        return dataSource.size
    }


}