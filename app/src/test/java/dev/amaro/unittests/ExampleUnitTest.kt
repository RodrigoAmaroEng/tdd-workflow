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

// STEP 4: One solution would be having another class to help defining rates.
// We need to make the tests ensure the proposed solution...
class CurrencyConverterTest {
    @Test
    fun `Converting MXN to USD should return 22_59`() {
        val provider = object : RateProvider {
            override fun rateFor(from: Currency, to: Currency): Rate {
                return 22.59.toBigDecimal()
            }
        }
        val converter = Converter(provider)
        assertEquals(22.59.toBigDecimal(), converter.convert("MXN", "USD"))
    }

    @Test
    fun `Converting USD to EUR should return 1_35`() {
        val provider = object : RateProvider {
            override fun rateFor(from: Currency, to: Currency): Rate {
                return 1.35.toBigDecimal()
            }
        }
        val converter = Converter(provider)
        assertEquals(1.35.toBigDecimal(), converter.convert("USD", "EUR"))
    }
}

// VERSION 3: ...And change the class to conform the solution proposed by our tests
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

