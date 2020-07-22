package dev.amaro.unittests

import java.math.BigDecimal

class Converter(
    private val rateProvider: RateProvider,
    private val taxProvider: TaxProvider = TaxProvider.NoTaxes
) {
    fun convert(
        from: Currency,
        to: Currency,
        amount: Amount = Amount(BigDecimal.ONE)
    ): Amount {
        return Amount(
            rateProvider.rateFor(from, to)
                .times(amount.value), to
        ) - taxProvider.taxFor(to)
    }
}
