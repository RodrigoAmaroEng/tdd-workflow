package dev.amaro.unittests

interface RateProvider {
    fun rateFor(from: Currency, to: Currency): Rate
}