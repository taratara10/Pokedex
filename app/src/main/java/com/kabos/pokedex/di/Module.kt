package com.kabos.pokedex.di

import android.app.Application
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.room.Room
import com.bumptech.glide.Glide
import com.kabos.pokedex.model.Pokemon
import com.kabos.pokedex.repository.PokeApiService
import com.kabos.pokedex.repository.PokemonDatabase
import com.kabos.pokedex.repository.PokemonRepository
import com.squareup.moshi.Moshi
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
    fun providePokeApiService(): PokeApiService =
            Retrofit.Builder()
                    .baseUrl(HTTPS_API_URL)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build()
                    .create(PokeApiService::class.java)

    @Singleton
    @Provides
    fun provideDatabase(app: Application): PokemonDatabase =
            Room.databaseBuilder(app, PokemonDatabase::class.java, "pokemon_database")
                    .fallbackToDestructiveMigration()
                    .build()

    @Singleton
    @Provides
    fun providePokeRepository(pokeApiService: PokeApiService, pokemonDb: PokemonDatabase): PokemonRepository =
            PokemonRepository(pokeApiService, pokemonDb)


    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, imageUrl: String?) {
        if (imageUrl != null) {
            Glide.with(view.context)
                .load(imageUrl)
                .into(view)
        }
    }
}
