package com.example.EnglishDictionary.Data.Repos

import androidx.lifecycle.MutableLiveData
import com.example.EnglishDictionary.*
import com.example.EnglishDictionary.Domain.Repos.RemoteRepository
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RemoteRepositoryImp @Inject constructor() : RemoteRepository {

    val oxfordJsonResult = MutableLiveData<EnglishWord?>()
    val otherDefinitionsAndExamples = MutableLiveData<List<Subsense?>?>()


    override fun getWordDetails(word: String) {
        DicAPIAccess.DictionaryRes.getRes(word).enqueue(object : Callback<oxfordJSON> {

            override fun onResponse(call: Call<oxfordJSON>, response: Response<oxfordJSON>) {
                if(response.body() == null) {
                    oxfordJsonResult.value = null
                    return
                }
                oxfordJsonResult.value = wordToAddGenerator(response.body()!! , word)
                otherDefinitionsAndExamples.value = otherDefinitionsAndExamplesGenerator(response.body()!! , word)
            }

            override fun onFailure(call: Call<oxfordJSON>, t: Throwable) {
                oxfordJsonResult.value = null
            }
        })
    }

    override fun getWordLiveDataObj(): Pair<MutableLiveData<EnglishWord?>, MutableLiveData<List<Subsense?>?>> {
        return Pair(oxfordJsonResult , otherDefinitionsAndExamples)
    }

    private fun wordToAddGenerator(messyOxfordJson : oxfordJSON, word : String) : EnglishWord{
        val englishWord = EnglishWord(word)
        val entries = messyOxfordJson.results[0].lexicalEntries!![0]!!.entries!![0]!!

        //word etymologies
        try {
            val etymology = entries.etymologies.toString().replace(";", "\n-", true).run {
                "- " + substring(1, lastIndex)
            }
            englishWord.etymology = etymology
        }catch (e : Exception){}

        //word category
        try {
            val category = messyOxfordJson.results[0].lexicalEntries!![0]!!.lexicalCategory?.text
            englishWord.category = category
        }catch (e : Exception){}


        //word main notes
        try {
            englishWord.normNote = entries.notes!![0]!!.text
        }catch (e : Exception){}

        //word definitions
        try {
            val definitions = entries.senses!![0]!!.definitions.toString().replace(";", "\n-", true).run {
                "- " + substring(1, lastIndex)
            }
            englishWord.primDefs = definitions
        }catch (e : Exception){}

        //word primary examples
        try {
            val examplesList = entries.senses!![0]!!.examples!!
            var counter = 1
            var examples = StringBuilder()
            for (i in examplesList) {
                examples.append("$counter-${i!!.text}\n")
                ++counter
            }
            englishWord.primExamples = examples.toString().trim()
        }catch (e : Exception){}

        return englishWord
    }

    private fun otherDefinitionsAndExamplesGenerator(messyOxfordJson : oxfordJSON , word : String):List<Subsense?>?{
        val entries = messyOxfordJson.results[0].lexicalEntries!![0]!!.entries!![0]!!

        //word other definitions and examples
        val result = entries.senses!![0]!!.subsenses

        return result
    }
}