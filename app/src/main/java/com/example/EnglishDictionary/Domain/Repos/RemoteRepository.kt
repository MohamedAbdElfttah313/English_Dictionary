package com.example.EnglishDictionary.Domain.Repos

import androidx.lifecycle.MutableLiveData
import com.example.EnglishDictionary.EnglishWord
import com.example.EnglishDictionary.OtherDefsAndExam
import com.example.EnglishDictionary.Subsense

interface RemoteRepository {

    fun getWordDetails(word : String)
    fun getWordLiveDataObj () : Pair<MutableLiveData<EnglishWord?>,MutableLiveData<List<Subsense?>?>>

}