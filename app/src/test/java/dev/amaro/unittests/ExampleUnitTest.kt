package dev.amaro.unittests

import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal
import java.math.RoundingMode


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
        val converter = Converter(provideRateAs(1.35), provideTaxAs(2.5))
        val amount = converter.convert("USD", "EUR", amountFor(2))
        assertEquals("2.63 EUR", amount.toString())
    }

    private fun provideRateAs(rate: Double) = object : RateProvider {
        override fun rateFor(from: Currency, to: Currency): Rate {
            return rate.toBigDecimal()
        }
    }

    private fun provideTaxAs(value: Double) = object : TaxProvider {
        override fun taxFor(target: Currency): Tax {
            return Tax(value)
        }
    }

    private fun amountFor(value: Int) = Amount(value.toBigDecimal())
}




