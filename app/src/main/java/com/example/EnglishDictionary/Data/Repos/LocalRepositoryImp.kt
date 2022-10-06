package com.example.EnglishDictionary.Data.Repos

import com.example.EnglishDictionary.Data.Local.EnglishWordDB
import com.example.EnglishDictionary.Domain.Repos.LocalRepository
import com.example.EnglishDictionary.EnglishWord
import com.example.EnglishDictionary.OtherDefsAndExam
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class LocalRepositoryImp @Inject constructor(private val wordsDB : EnglishWordDB) : LocalRepository {


    override suspend fun saveWordToDatabase(word: EnglishWord) {
        wordsDB.EnglishWordDAO.addWord(word)
    }

    override suspend fun saveOthersToDatabase(others: OtherDefsAndExam) {
        wordsDB.EnglishWordDAO.addOthers(others)
    }
}