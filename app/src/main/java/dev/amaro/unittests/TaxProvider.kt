package dev.amaro.unittests

interface TaxProvider {
    fun taxFor(target: Currency): Tax

    object NoTaxes : TaxProvider {
        override fun taxFor(target: Currency): Tax = Tax(0.0)
    }
}