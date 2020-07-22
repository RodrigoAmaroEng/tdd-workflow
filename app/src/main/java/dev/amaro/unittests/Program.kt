package dev.amaro.unittests

// Now have most of our setup in place lets talk about user interface
// This is a proposal to use our feature trough terminal
object Program {

    private val converter = Converter(SimpleRateProvider, TaxProvider.NoTaxes)

    @JvmStatic
    fun main(params: Array<String>) {
        println("How much you wanna convert?")
        val amount = readLine()?.let { Amount(it.toBigDecimal()) }
        println("From which currency?")
        val from = readLine() as? Currency
        println("To which currency?")
        val to = readLine() as? Currency
        val final = converter.convert(from!!, to!!, amount!!)
        println("$amount $from is converted to $final")
    }
}

object SimpleRateProvider : RateProvider {
    private val base: Map<Pair<Currency, Currency>, Rate> = mapOf(
        Pair("MXN", "USD") to Rate(0.045),
        Pair("MXN", "BRL") to Rate(0.23),
        Pair("MXN", "EUR") to Rate(0.039),
        Pair("USD", "MXN") to Rate(22.29),
        Pair("USD", "EUR") to Rate(0.86),
        Pair("USD", "BRL") to Rate(5.11),
        Pair("EUR", "MXN") to Rate(25.8),
        Pair("EUR", "USD") to Rate(1.16),
        Pair("EUR", "BRL") to Rate(5.92),
        Pair("BRL", "MXN") to Rate(4.36),
        Pair("BRL", "USD") to Rate(0.20),
        Pair("BRL", "EUR") to Rate(0.17)
    )

    override fun rateFor(from: Currency, to: Currency): Rate {
        return base[Pair(from, to)] ?: Rate(0)
    }
}