package com.yavuzbahceci.gitfetcher.util.converter

interface ConverterProvider<S, P> {
    fun provide(source: S): Converter<S, P>
}