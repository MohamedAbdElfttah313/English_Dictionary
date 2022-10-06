package com.example.EnglishDictionary.DI

import android.app.Application
import android.content.Context
import com.example.EnglishDictionary.Data.Local.EnglishWordDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Providers {

    @Provides
    @Singleton
    fun getDaoRef(app: Application): EnglishWordDB{
        return EnglishWordDB.getInstance(app)
    }
}