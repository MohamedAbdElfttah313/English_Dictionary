package com.example.EnglishDictionary.Domain.UseCases

interface GetWordDetailsUseCase {
    operator fun invoke(word: String)
}