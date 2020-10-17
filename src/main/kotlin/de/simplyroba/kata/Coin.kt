package de.simplyroba.kata

import java.math.BigDecimal

/**
 * @author simplyroba
 */
enum class Coin(
    val weightInGram: Float,
    val diameterInMillimeter: Float,
    val value: BigDecimal,
) {

    NICKEL(5f, 21.21f, BigDecimal("0.05")),
    DIME(2.268f, 17.91f, BigDecimal("0.1")),
    QUARTER(5.67f, 24.26f, BigDecimal("0.25"));

    companion object {
        fun getCoinByWeightAndDiameter(weightInGram: Float, diameterInMillimeter: Float): Coin {
            return values()
                .filter { coin -> coin.weightInGram.equals(weightInGram) }
                .first { coin -> coin.diameterInMillimeter.equals(diameterInMillimeter) }
        }
    }
}
