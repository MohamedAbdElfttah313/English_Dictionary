package com.example.EnglishDictionary.UI.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.EnglishDictionary.Data.Local.EnglishWordDB
import com.example.EnglishDictionary.EnglishWordWithSubSensnes
import com.example.EnglishDictionary.R
import com.example.EnglishDictionary.UI.Adapters.DetailAdapter
import kotlinx.android.synthetic.main.fragment_cached_word_detail.*

import kotlinx.coroutines.*


class CachedWordDetailFragment : Fragment() {

    lateinit var wordWithDetails : EnglishWordWithSubSensnes
    lateinit var coroScope : CoroutineScope

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cached_word_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val DBDao = EnglishWordDB.getInstance(requireContext()).EnglishWordDAO
        val specifiedWord = requireArguments().getString("wordDetails").toString()
        coroScope = CoroutineScope(Dispatchers.Main)

        coroScope.launch {
            withContext(Dispatchers.IO){
                wordWithDetails = DBDao.getWordDetails(specifiedWord)
            }

            withContext(Dispatchers.Main){
                val enWord = wordWithDetails.EnglishWord
                val listOFOthers = wordWithDetails.otherDefAndExaS

                wordType2.text = enWord.category
                historicalNote2.text = enWord.etymology
                normNote2.text = enWord.normNote
                primDefs2.text = enWord.primDefs
                primExa2.text = enWord.primExamples

                myRecView2.layoutManager = LinearLayoutManager(activity)
                myRecView2.itemAnimator = DefaultItemAnimator()
                myRecView2.adapter = DetailAdapter(listOFOthers)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroScope.cancel()
    }




}