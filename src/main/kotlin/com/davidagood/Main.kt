package com.davidagood

fun main() {
    val fileAccess = ResourceFileReader()
    val resourceRelativeFilePaths = listOf("words.txt", "buildLog.txt", "does_not_exist.txt")
    resourceRelativeFilePaths
            .map { fileAccess.readLines(it) }
            .forEach { println(it) }
}