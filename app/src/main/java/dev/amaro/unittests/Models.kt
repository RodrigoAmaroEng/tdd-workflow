package dev.amaro.unittests

import java.math.BigDecimal
import java.math.RoundingMode

typealias Currency = String

data class Tax(private val _value: BigDecimal) {
    constructor(value: Double) : this(value.toBigDecimal())

    val value: BigDecimal = _value.divide(BigDecimal(100))
}

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