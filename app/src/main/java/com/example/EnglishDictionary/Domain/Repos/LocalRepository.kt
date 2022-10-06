package com.example.EnglishDictionary.Domain.Repos

import com.example.EnglishDictionary.EnglishWord
import com.example.EnglishDictionary.OtherDefsAndExam

interface LocalRepository {

    suspend fun saveWordToDatabase(word : EnglishWord)
    suspend fun saveOthersToDatabase(others : OtherDefsAndExam)
}