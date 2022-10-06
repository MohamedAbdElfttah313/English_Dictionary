package com.example.EnglishDictionary.DI

import com.example.EnglishDictionary.Data.Repos.RemoteRepositoryImp
import com.example.EnglishDictionary.Domain.Repos.RemoteRepository
import com.example.EnglishDictionary.Domain.UseCases.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCasesInjection {

    @Binds
    @Singleton
    abstract fun wordsDetailsUseCase(wordDetailsUseCaseImp: GetWordDetailsUseCaseImp) : GetWordDetailsUseCase

    @Binds
    @Singleton
    abstract fun liveDataUseCase(getWordLDObjUseCase: GetWordLDObjUseCaseImp) : GetWordLDObjUseCase

    @Binds
    @Singleton
    abstract fun saveWordUseCase(saveWordDB: SaveWordToDBUseCaseImp) : SaveWordToDBUseCase

    @Binds
    @Singleton
    abstract fun saveOthersUseCase(saveOthersDB: SaveOthersToBDUsaCaseImp) : SaveOthersToBDUsaCase

}