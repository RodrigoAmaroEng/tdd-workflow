package dev.amaro.unittests

import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal
import java.math.RoundingMode

/**
App Proposal: Currency converter
Features:
- Accept many currencies
- Must check current conversion rate automatically
- Must apply taxes from each country
 **/

// STEP 9: Now we can start working in the last feature to apply taxes.
// As always we take the easiest path first
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

    @Test
    fun `Convert value discounting tax`() {
        val converter = Converter(provideRateAs(1.35))
        val amount = converter.convert("USD", "EUR", amountFor(2), 2.5)
        assertEquals("2.63 EUR", amount.toString())
    }

    private fun provideRateAs(rate: Double) = object : RateProvider {
        override fun rateFor(from: Currency, to: Currency): Rate {
            return rate.toBigDecimal()
        }
    }

    private fun amountFor(value: Int) = Amount(value.toBigDecimal())
}


// VERSION 6: Changes to consider the Tax when providing the final conversion amount
typealias Currency = String

data class Amount(
    val value: BigDecimal,
    val currency: Currency? = null
) {
    override fun toString(): String {
        // Fixing decimal places, something that we haven't figured out till now
        return "${value.setScale(2, RoundingMode.HALF_UP)} $currency"
    }
}

typealias Rate = BigDecimal

interface RateProvider {
    fun rateFor(from: Currency, to: Currency): Rate
}

class Converter(private val rateProvider: RateProvider) {
    fun convert(
        from: Currency,
        to: Currency,
        amount: Amount = Amount(BigDecimal.ONE),
        tax: Double = 0.0
    ): Amount {
        val taxMultiplier = BigDecimal(1.0 - (tax/100))
        return Amount(rateProvider.rateFor(from, to).times(amount.value).times(taxMultiplier), to)
    }
}

