package com.example.EnglishDictionary.Domain.UseCases

import com.example.EnglishDictionary.OtherDefsAndExam
import com.example.EnglishDictionary.Subsense

interface SaveOthersToBDUsaCase {
    suspend operator fun invoke(others : List<Subsense?>? , word : String)
}