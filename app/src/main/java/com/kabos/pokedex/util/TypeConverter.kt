package com.kabos.pokedex.util

import androidx.room.TypeConverter
import com.kabos.pokedex.model.*
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types.newParameterizedType

class SpritesConverter {
    @TypeConverter
    fun fromString(value: String?): Sprites? {
        return value?.let { Sprites(it, it) }
    }

    @TypeConverter
    fun spriteToString(data: Sprites?): String? {
        return data?.front_default
    }
}

class TypesConverter {
    private val moshi = Moshi.Builder().build()
    val type = newParameterizedType(List::class.java,Types::class.java)
    val listAdapter: JsonAdapter<List<Types>> = moshi.adapter(type)

    @TypeConverter
    fun fromString(value: String?): List<Types>? {
        return value?.let { listAdapter.fromJson(value) }
    }

    @TypeConverter
    fun nameToString(data: List<Types>?): String? {
        return listAdapter.toJson(data)
    }
}


class NameConverter {
    private val moshi = Moshi.Builder().build()
    val type = newParameterizedType(List::class.java,Name::class.java)
    val listAdapter: JsonAdapter<List<Name>> = moshi.adapter(type)

    @TypeConverter
    fun fromString(value: String?): List<Name>? {
        return value?.let { listAdapter.fromJson(value) }
    }

    @TypeConverter
    fun nameToString(data: List<Name>?): String? {
        return listAdapter.toJson(data)
    }
}

class FlavorTextEntryConverter {
    private val moshi = Moshi.Builder().build()
    val type = newParameterizedType(List::class.java,FlavorTextEntry::class.java)
    val listAdapter: JsonAdapter<List<FlavorTextEntry>> = moshi.adapter(type)

    @TypeConverter
    fun fromString(value: String?): List<FlavorTextEntry>? {
        return value?.let { listAdapter.fromJson(value) }
    }

    @TypeConverter
    fun nameToString(data: List<FlavorTextEntry>?): String? {
        return listAdapter.toJson(data)
    }
}


class GeneraConverter {
    private val moshi = Moshi.Builder().build()
    private val type = newParameterizedType(List::class.java,Genera::class.java)
    private val listAdapter: JsonAdapter<List<Genera>> = moshi.adapter(type)
    @TypeConverter
    fun fromString(value: String?): List<Genera>? {
        return value?.let { listAdapter.fromJson(value) }
    }

    @TypeConverter
    fun generaToString(data: List<Genera>?): String? {
        return listAdapter.toJson(data)
    }
}













