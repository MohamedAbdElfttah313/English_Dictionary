package com.example.EnglishDictionary.Domain.UseCases

import com.example.EnglishDictionary.Domain.Repos.LocalRepository
import com.example.EnglishDictionary.OtherDefsAndExam
import com.example.EnglishDictionary.Subsense
import javax.inject.Inject

class SaveOthersToBDUsaCaseImp @Inject constructor(private val repo : LocalRepository) : SaveOthersToBDUsaCase {
    override suspend fun invoke(others: List<Subsense?>? , word : String) {
        others?.let {res->
            for (i in res) {
                val otherDefs = if (i?.definitions == null) "None !" else i.definitions.toString().run {
                    this.substring(1, this.lastIndex)
                }
                val otherExamples = if (i?.examples == null) "None !" else i.examples[0]!!.text
                val othersToAdd = OtherDefsAndExam(otherDefs, otherExamples, word)

                repo.saveOthersToDatabase(othersToAdd)
            }
        }
    }
}