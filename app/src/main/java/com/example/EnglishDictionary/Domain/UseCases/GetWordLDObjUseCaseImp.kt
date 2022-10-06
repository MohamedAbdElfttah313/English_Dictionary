package com.example.EnglishDictionary.Domain.UseCases

import androidx.lifecycle.MutableLiveData
import com.example.EnglishDictionary.Domain.Repos.RemoteRepository
import com.example.EnglishDictionary.EnglishWord
import com.example.EnglishDictionary.Subsense
import javax.inject.Inject

class GetWordLDObjUseCaseImp @Inject constructor(private val repo : RemoteRepository ) : GetWordLDObjUseCase{
    override fun invoke(): Pair<MutableLiveData<EnglishWord?>, MutableLiveData<List<Subsense?>?>> {
        return repo.getWordLiveDataObj()
    }
}