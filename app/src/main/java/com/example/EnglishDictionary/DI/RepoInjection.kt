package com.example.EnglishDictionary.DI

import com.example.EnglishDictionary.Data.Repos.LocalRepositoryImp
import com.example.EnglishDictionary.Data.Repos.RemoteRepositoryImp
import com.example.EnglishDictionary.Domain.Repos.LocalRepository
import com.example.EnglishDictionary.Domain.Repos.RemoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoInjection {

    @Binds
    @Singleton
    abstract fun getRemoteRepo(remoteRepo : RemoteRepositoryImp) : RemoteRepository

    @Binds
    @Singleton
    abstract fun getLocalRepo(localRepo : LocalRepositoryImp) : LocalRepository


}