package de.simplyroba.kata

import java.math.BigDecimal

/**
 * @author simplyroba
 */
class VendingMachine {

    private val currentInsertedCoins: MutableList<Coin> = ArrayList()

    fun insertObject(insertedObject: InsertionObject) {
        if (insertedObject.isValidCoin()) {
            currentInsertedCoins.add(insertedObject.toCoin())
        }
    }

    fun currentAmount(): BigDecimal {
        return currentInsertedCoins.sumOf { coin -> coin.value }
    }
}

class InsertionObject(
    private val weightInGram: Float,
    private val diameterInMillimeter: Float) {

    fun toCoin(): Coin {
        try {
            return Coin.getCoinByWeightAndDiameter(weightInGram, diameterInMillimeter)
        } catch (ex: NoSuchElementException) {
            throw IllegalStateException("No valid coin with this dimensions found.", ex)
        }
    }

    fun getValue(): BigDecimal {
        return try {
            toCoin().value
        } catch (ex: IllegalStateException) {
            BigDecimal.ZERO
        }
    }

    fun isValidCoin(): Boolean {
        return getValue() > BigDecimal.ZERO
    }
}
