package de.simplyroba.kata

import java.math.BigDecimal
import kotlin.text.StringBuilder

/**
 * @author simplyroba
 */
class VendingMachine {

    private val currentInsertedCoins: MutableList<Coin> = ArrayList()
    private val coinReturn: MutableList<InsertionObject> = ArrayList()

    companion object {
        const val INSERT_COIN = "INSERT COIN"
        const val CURRENT_AMOUNT = "CURRENT AMOUNT"
        const val COIN_RETURN = "COIN RETURN"
    }

    fun insertObject(insertedObject: InsertionObject) {
        if (insertedObject.isValidCoin()) {
            currentInsertedCoins.add(insertedObject.toCoin())
        } else {
            coinReturn.add(insertedObject)
        }
    }

    fun currentAmount(): BigDecimal {
        return currentInsertedCoins.sumOf { coin -> coin.value }
    }

    fun objectsInCoinReturn(): MutableList<InsertionObject> {
        return coinReturn
    }

    fun display(): String {
        val sb = StringBuilder()

        if (currentInsertedCoins.isEmpty()) {
            sb.appendLine(INSERT_COIN)
        } else {
            sb
                .append(CURRENT_AMOUNT)
                .append(": ")
                .append(currentAmount())
                .appendLine(" $")
        }

        if (coinReturn.isNotEmpty()) {
            sb
                .append(COIN_RETURN)
                .append(": ")
                .append(coinReturn)
                .appendLine()
            coinReturn.clear()
        }

        return sb.toString();
    }
}

data class InsertionObject(
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
