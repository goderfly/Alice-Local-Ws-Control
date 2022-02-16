package utils

import java.io.File
import kotlin.reflect.KProperty

class StorableLong<in R> {
    operator fun getValue(thisRef: R, property: KProperty<*>): Long {
        return runCatching { File("cache/${property.name}").inputStream().bufferedReader().use { it.readText() } }
            .getOrElse { "0" }.toLong()
    }

    operator fun setValue(thisRef: R, property: KProperty<*>, value: Long) {
        File("cache").mkdir()
        File("cache/${property.name}").apply { writeText(value.toString()) }
    }
}