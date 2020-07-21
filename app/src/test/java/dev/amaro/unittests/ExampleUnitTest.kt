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

// STEP 6: We can start informing the amount to convert
class CurrencyConverterTest {

    @Test
    fun `Converting MXN to USD should return 22_59`() {
        val converter = Converter(provideRateAs(22.59))
        assertEquals(22.59.toBigDecimal(), converter.convert("MXN", "USD"))
    }

    @Test
    // Lets change this test to conform the proposal
    fun `Converting 2 USD to EUR should return 2_70`() {
        val converter = Converter(provideRateAs(1.35))
        assertEquals(BigDecimal("2.70"), converter.convert("USD", "EUR", BigDecimal(2)))
    }

    private fun provideRateAs(rate: Double) = object : RateProvider {
        override fun rateFor(from: Currency, to: Currency): Rate {
            return rate.toBigDecimal()
        }
    }
}


typealias Currency = String
typealias Amount = BigDecimal
typealias Rate = BigDecimal

interface RateProvider {
    fun rateFor(from: Currency, to: Currency): Rate
}
// VERSION 4: We change the solution to calculate the converted amount
class Converter(private val rateProvider: RateProvider) {
    fun convert(from: Currency, to: Currency, amount: Amount = BigDecimal.ONE): Amount {
        return rateProvider.rateFor(from, to).times(amount)
    }
}

