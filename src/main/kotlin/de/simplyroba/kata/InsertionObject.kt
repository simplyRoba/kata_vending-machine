package de.simplyroba.kata

import java.math.BigDecimal

data class InsertionObject(
    private val weightInGram: Float,
    private val diameterInMillimeter: Float) {

    fun toCoin(): Coin? {
        return Coin.getCoinByWeightAndDiameter(weightInGram, diameterInMillimeter)
    }

    fun getValue(): BigDecimal {
        return toCoin()?.value ?: BigDecimal.ZERO
    }
}
