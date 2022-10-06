package com.example.EnglishDictionary.Domain.UseCases

import com.example.EnglishDictionary.Domain.Repos.RemoteRepository
import javax.inject.Inject

class GetWordDetailsUseCaseImp @Inject constructor(private val repo : RemoteRepository) : GetWordDetailsUseCase {
    override fun invoke(word: String) {
     repo.getWordDetails(word)
    }
}