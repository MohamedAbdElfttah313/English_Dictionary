package com.example.EnglishDictionary.UI.Activities

import androidx.lifecycle.*
import com.example.EnglishDictionary.Domain.UseCases.GetWordDetailsUseCase
import com.example.EnglishDictionary.Domain.UseCases.GetWordLDObjUseCase
import com.example.EnglishDictionary.Domain.UseCases.SaveOthersToBDUsaCase
import com.example.EnglishDictionary.Domain.UseCases.SaveWordToDBUseCase
import com.example.EnglishDictionary.EnglishWord
import com.example.EnglishDictionary.OtherDefsAndExam
import com.example.EnglishDictionary.Subsense
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchActivityViewModel @Inject constructor() : ViewModel(){


    @Inject
    lateinit var getWordDetailsUseCase : GetWordDetailsUseCase

    @Inject
    lateinit var getWordLiveDataObjsUseCase : GetWordLDObjUseCase

    @Inject
    lateinit var saveWordDbUseCase : SaveWordToDBUseCase

    @Inject
    lateinit var saveOthersToDbUseCase : SaveOthersToBDUsaCase


    var detailedWordLiveData = MutableLiveData<EnglishWord?>()
    var otherDefinitionsAndExamples = MutableLiveData<List<Subsense?>?>()

    val coroScope = CoroutineScope(Dispatchers.IO)



    fun initialize(){
        with(getWordLiveDataObjsUseCase()){
            detailedWordLiveData = first
            otherDefinitionsAndExamples = second
        }
    }

    fun getDetailedWord(word : String){
        getWordDetailsUseCase(word)
    }

    fun saveWordToDb(word : EnglishWord){
        coroScope.launch {
            saveWordDbUseCase(word)
        }
    }

    fun saveOthersToDb(others : List<Subsense?>? , word : String){
        coroScope.launch {
            saveOthersToDbUseCase(others , word)
        }
    }



    override fun onCleared() {
        coroScope.cancel()
        super.onCleared()
    }

}