package com.example.EnglishDictionary.Domain.UseCases

import com.example.EnglishDictionary.Data.Local.EnglishWordDB
import com.example.EnglishDictionary.Domain.Repos.LocalRepository
import com.example.EnglishDictionary.EnglishWord
import javax.inject.Inject

class SaveWordToDBUseCaseImp @Inject constructor(private val repo : LocalRepository) : SaveWordToDBUseCase {
    override suspend fun invoke(word: EnglishWord) {
        repo.saveWordToDatabase(word)
    }
}