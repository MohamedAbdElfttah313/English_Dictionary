package com.example.EnglishDictionary.Data.Local

import androidx.room.*
import com.example.EnglishDictionary.EnglishWordWithSubSensnes
import com.example.EnglishDictionary.EnglishWord
import com.example.EnglishDictionary.OtherDefsAndExam


@Dao
interface EnglishWordDAo {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addWord(enWord : EnglishWord)

    @Insert
    fun addOthers(others : OtherDefsAndExam)

    @Update
    fun updateWord(enword : EnglishWord)


    @Query("SELECT wordPrim FROM ENGLISH_WORDS ORDER BY wordPrim DESC")
    fun getAllWords() : List<String>

    @Query("SELECT Favourite FROM ENGLISH_WORDS WHERE wordPrim=:word")
    fun isFav(word : String) : Boolean

    @Transaction
    @Query("SELECT * FROM ENGLISH_WORDS WHERE wordPrim=:word")
    fun getWordDetails(word : String) : EnglishWordWithSubSensnes

    @Query("SELECT * FROM ENGLISH_WORDS WHERE wordPrim=:word")
    fun getWordToUpdate(word : String) : EnglishWord

    @Query("Select wordPrim FROM ENGLISH_WORDS WHERE Favourite =:True")
    fun getAllFav(True : Boolean) : List<String>

    @Query("SELECT Favourite FROM ENGLISH_WORDS WHERE wordPrim = :word")
    fun isFavorite(word : String): Boolean?


}