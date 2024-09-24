package com.example.dictionary_app.dependencyInjection

import com.example.dictionary_app.data.repository.DictionaryRepositoryImplementation
import com.example.dictionary_app.domain.repository.DictionaryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindDictionaryRepository(
        dictionaryRepositoryImplementation: DictionaryRepositoryImplementation
    ) : DictionaryRepository
}