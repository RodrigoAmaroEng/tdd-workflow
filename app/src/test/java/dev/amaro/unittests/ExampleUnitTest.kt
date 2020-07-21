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

// STEP 10: There is room to improve how we handle taxes, lets refactor
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
        val amount = converter.convert("USD", "EUR", amountFor(2), Tax(2.5))
        assertEquals("2.63 EUR", amount.toString())
    }

    private fun provideRateAs(rate: Double) = object : RateProvider {
        override fun rateFor(from: Currency, to: Currency): Rate {
            return rate.toBigDecimal()
        }
    }

    private fun amountFor(value: Int) = Amount(value.toBigDecimal())
}


// VERSION 7: Changes to improve dealing with Taxes
typealias Currency = String

// This would help us dealing with the calculation involved about Taxes
class Tax(value: BigDecimal) {
    constructor(value: Double) : this(value.toBigDecimal())

    val value: BigDecimal = value.divide(BigDecimal(100))
}

data class Amount(
    val value: BigDecimal,
    val currency: Currency? = null
) {
    override fun toString(): String {

        return "${value.setScale(2, RoundingMode.HALF_UP)} $currency"
    }

    // Adding this makes sentences more readable
    operator fun minus(t: Tax): Amount {
        return Amount(this.value.multiply(BigDecimal.ONE.minus(t.value)), this.currency)
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
        tax: Tax = Tax(BigDecimal.ZERO)
    ): Amount {
        return Amount(rateProvider.rateFor(from, to).times(amount.value), to) - tax
    }
}

