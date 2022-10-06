package com.example.EnglishDictionary.Data.Local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.EnglishDictionary.EnglishWord
import com.example.EnglishDictionary.OtherDefsAndExam


@Database(entities = [EnglishWord::class , OtherDefsAndExam::class] ,version = 1 , exportSchema = false)
abstract class EnglishWordDB : RoomDatabase() {
    abstract val EnglishWordDAO: EnglishWordDAo

    companion object{

        @Volatile
        private var INSTANCE : EnglishWordDB? = null

        fun getInstance(context: Context) : EnglishWordDB {

            synchronized(this){
                var instance = INSTANCE
                if(instance == null){

                    instance = Room.databaseBuilder(context , EnglishWordDB::class.java ,"English_word_database")
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }


    }


}