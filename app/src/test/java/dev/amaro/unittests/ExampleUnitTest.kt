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

// STEP 8: Again we can do some refactor to make the code more readable
class CurrencyConverterTest {

    @Test
    fun `Converting MXN to USD should return 22_59`() {
        val converter = Converter(provideRateAs(22.59))
        assertEquals(22.59.toBigDecimal(), converter.convert("MXN", "USD").value)
    }

    @Test
    fun `Converting 2 USD to EUR should return 2_70`() {
        val converter = Converter(provideRateAs(1.35))
        val amount = converter.convert("USD", "EUR", amountFor(2))
        assertEquals(BigDecimal("2.70"), amount.value)
    }

    @Test
    fun `When print the amount it should present its currency`() {
        val converter = Converter(provideRateAs(1.35))
        val amount = converter.convert("USD", "EUR", amountFor(2))
        assertEquals("2.70 EUR", amount.toString())
    }

    private fun provideRateAs(rate: Double) = object : RateProvider {
        override fun rateFor(from: Currency, to: Currency): Rate {
            return rate.toBigDecimal()
        }
    }
    private fun amountFor(value : Int) = Amount(value.toBigDecimal())
}


typealias Currency = String

data class Amount(
    val value: BigDecimal,
    val currency: Currency? = null
) {
    override fun toString(): String {
        return "$value $currency"
    }
}

typealias Rate = BigDecimal

interface RateProvider {
    fun rateFor(from: Currency, to: Currency): Rate
}

class Converter(private val rateProvider: RateProvider) {
    fun convert(from: Currency, to: Currency, amount: Amount = Amount(BigDecimal.ONE)): Amount {
        return Amount(rateProvider.rateFor(from, to).times(amount.value), to)
    }
}

