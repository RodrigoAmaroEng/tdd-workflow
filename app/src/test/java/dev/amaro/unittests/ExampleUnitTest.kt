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

// STEP 3: Exercise current solution with other similar case
class CurrencyConverterTest {
    @Test
    fun `Converting MXN to USD should return 22_59`() {
        val converter = Converter()
        assertEquals(22.59.toBigDecimal(), converter.convert("MXN", "USD"))
    }

    @Test
    fun `Converting USD to EUR should return 1_35`() {
        val converter = Converter()
        assertEquals(1.35.toBigDecimal(), converter.convert("USD", "EUR"))
    }
}

// The new test will force us to rethink current solution
typealias Currency = String
typealias Amount = BigDecimal
class Converter {
    fun convert(from: Currency, to: Currency): Amount {
        return 22.59.toBigDecimal()
    }
}


