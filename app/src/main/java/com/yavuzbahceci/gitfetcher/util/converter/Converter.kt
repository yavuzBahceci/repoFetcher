package com.yavuzbahceci.gitfetcher.util.converter

interface Converter<S, P> {

    val source: S
    fun convert(): P
}