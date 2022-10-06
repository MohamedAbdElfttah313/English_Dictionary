package com.example.EnglishDictionary.Domain.UseCases

import androidx.lifecycle.MutableLiveData
import com.example.EnglishDictionary.EnglishWord
import com.example.EnglishDictionary.Subsense

interface GetWordLDObjUseCase {
    operator fun invoke(): Pair<MutableLiveData<EnglishWord?>, MutableLiveData<List<Subsense?>?>>
}