package dev.amaro.unittests

import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

/**
App Proposal: Currency converter
Features:
- Accept many currencies
- Must check current conversion rate automatically
- Must apply taxes from each country
 **/

// STEP 5: Now we have the chance to improve our test's quality by refactoring them
class CurrencyConverterTest {

    @Test
    fun `Converting MXN to USD should return 22_59`() {
        val converter = Converter(provideRateAs(22.59))
        assertEquals(22.59.toBigDecimal(), converter.convert("MXN", "USD"))
    }

    @Test
    fun `Converting USD to EUR should return 1_35`() {
        val converter = Converter(provideRateAs(1.35))
        assertEquals(1.35.toBigDecimal(), converter.convert("USD", "EUR"))
    }

    private fun provideRateAs(rate: Double) = object : RateProvider {
        override fun rateFor(from: Currency, to: Currency): Rate {
            return rate.toBigDecimal()
        }
    }
}

// No changes in our implementation at this point
typealias Currency = String
typealias Amount = BigDecimal
typealias Rate = BigDecimal

interface RateProvider {
    fun rateFor(from: Currency, to: Currency): Rate
}

class Converter(private val rateProvider: RateProvider) {
    fun convert(from: Currency, to: Currency): Amount {
        return rateProvider.rateFor(from, to)
    }
}

