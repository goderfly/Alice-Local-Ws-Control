package com.mirbor

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

/**
 * Created by Alexey Goncharov on 14.06.2018.
 */

val mapper = jacksonObjectMapper()

// Получает объект из Json-строки
inline fun <reified T> String.fromJson(): T =
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).readValue(this, T::class.java)

// Получает массив объектов из Json-строки
inline fun <reified T> String.arrayFromJson(): List<T> {
    val listType = mapper.typeFactory.constructCollectionType(ArrayList::class.java, T::class.java)
    return mapper
        .configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true)
        .configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true)
        .readValue(this, listType)
}

// Преобразует объект в json-строку
fun Any.toJson(): String = mapper
    .configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true)
    .configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true)
    .writer()
    .writeValueAsString(this)
