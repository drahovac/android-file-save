package com.chicco.filesave.utils

fun String?.nullIfEmpty(): String? {
    return this?.takeUnless { it.isEmpty() }
}

fun String.suffix(): String? {
    return substringAfterLast(DOT, EMPTY_STRING).nullIfEmpty()
}