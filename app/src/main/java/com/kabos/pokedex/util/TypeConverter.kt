package com.kabos.pokedex.util

import androidx.room.TypeConverter
import com.kabos.pokedex.model.*

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
    @TypeConverter
    fun fromString(value: String?): Types? {
        return value?.let { Types(Type(it), null) }
    }

    @TypeConverter
    fun nameToString(data: Types?): String? {
        return data?.toString()
    }
}

class TypeConverter {
    @TypeConverter
    fun fromString(value: String?): Type? {
        return value?.let { Type(it) }
    }

    @TypeConverter
    fun typeToString(data: Type?): String? {
        return data?.name
    }
}


class NameConverter {
    @TypeConverter
    fun fromString(value: String?): Name? {
        return value?.let { Name(it) }
    }

    @TypeConverter
    fun nameToString(data: Name?): String? {
        return data?.name
    }
}

class FlavorTextEntryConverter {
    @TypeConverter
    fun fromString(value: String?): FlavorTextEntry? {
        return value?.let { FlavorTextEntry(value, Language(value)) }
    }

    @TypeConverter
    fun nameToString(data: FlavorTextEntry?): String? {
        return data?.toString()
    }
}


class GeneraConverter {
    @TypeConverter
    fun fromString(value: String?): Genera? {
        return value?.let { Genera(it,null) }
    }

    @TypeConverter
    fun generaToString(data: Genera?): String? {
        return data?.genus
    }
}


class LanguageConverter {
    @TypeConverter
    fun fromString(value: String?): Language? {
        return value?.let { Language(it) }
    }

    @TypeConverter
    fun languageToString(data: Language?): String? {
        return data?.name
    }
}











