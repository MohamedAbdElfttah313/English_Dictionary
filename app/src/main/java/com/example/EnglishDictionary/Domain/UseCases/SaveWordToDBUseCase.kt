package com.example.EnglishDictionary.Domain.UseCases

import com.example.EnglishDictionary.EnglishWord

interface SaveWordToDBUseCase {
    suspend operator fun invoke(word : EnglishWord)
}