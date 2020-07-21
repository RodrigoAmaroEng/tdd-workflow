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

// STEP 2: Refactor current implementation to better describe and structure the solution
class CurrencyConverterTest {
    @Test
    fun `Converting MXN to USD should return 22_59`() {
        val converter = Converter()
        assertEquals(22.59.toBigDecimal(), converter.convert("MXN", "USD"))
    }
}

// VERSION 2: Minor improvements to better structure
typealias Currency = String
typealias Amount = BigDecimal

class Converter {
    fun convert(from: Currency, to: Currency): Amount {
        return 22.59.toBigDecimal()
    }
}



