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

// STEP 1: Prioritize most valuable deliverable
// In this case it can be converting (any) two currencies
class CurrencyConverterTest {
    @Test
    fun `Converting MXN to USD should return 22_59`() {
        val converter = Converter()
        assertEquals(22.59.toBigDecimal(), converter.convert("MXN", "USD"))
    }
}

// VERSION 1: Simple as possible to cover the proposed test
class Converter {
    fun convert(from: String, to: String): BigDecimal {
        return 22.59.toBigDecimal()
    }
}



