package com.kabos.pokedex.di

import com.kabos.pokedex.repository.PokeApiService
import com.kabos.pokedex.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

const val HTTPS_API_URL = "https://pokeapi.co/api/v2/"

@InstallIn(SingletonComponent::class)
@Module
object ApiModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit =
            Retrofit.Builder()
                    .baseUrl(HTTPS_API_URL)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build()

    @Singleton
    @Provides
    fun providePokeApiService(retrofit: Retrofit): PokeApiService =
            retrofit.create(PokeApiService::class.java)


    @Singleton
    @Provides
    fun providePokeRepository(pokeApiService: PokeApiService) =
            PokemonRepository(pokeApiService)
}