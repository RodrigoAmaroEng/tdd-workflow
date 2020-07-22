package dev.amaro.unittests

import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.reflect.KProperty

/**
App Proposal: Currency converter
Features:
- Accept many currencies
- Must check current conversion rate automatically
- Must apply taxes from each country
 **/

// STEP 11: As we know, taxes are given by country.
// Users are not interested in knowing each one so the App should retrieve and apply it automatically
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


// VERSION 8: We need to pass the TaxProvider to the Converter instance
typealias Currency = String

class Tax(value: BigDecimal) {
    constructor(value: Double) : this(value.toBigDecimal())

    val value: BigDecimal = value.divide(BigDecimal(100))
}

// This small change formats the value to prevent equality errors
data class Amount(
    private val _value: BigDecimal,
    val currency: Currency? = null
) {
    val value: BigDecimal
        get() = _value.setScale(2, RoundingMode.HALF_UP)

    override fun toString(): String {
        return "$value $currency"
    }

    operator fun minus(t: Tax): Amount {
        return Amount(this.value.multiply(BigDecimal.ONE.minus(t.value)), this.currency)
    }
}

typealias Rate = BigDecimal

interface RateProvider {
    fun rateFor(from: Currency, to: Currency): Rate
}
// Here is our TaxProvider with an option for no taxes
interface TaxProvider {
    fun taxFor(target: Currency): Tax

    object NoTaxes : TaxProvider {
        override fun taxFor(target: Currency): Tax = Tax(0.0)
    }
}

class Converter(
    private val rateProvider: RateProvider,
    private val taxProvider: TaxProvider = TaxProvider.NoTaxes
) {
    fun convert(
        from: Currency,
        to: Currency,
        amount: Amount = Amount(BigDecimal.ONE)
    ): Amount {
        return Amount(rateProvider.rateFor(from, to)
            .times(amount.value), to) - taxProvider.taxFor(to)
    }
}

