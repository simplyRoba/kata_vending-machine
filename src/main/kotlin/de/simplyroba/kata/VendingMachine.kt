package de.simplyroba.kata

import de.simplyroba.kata.Coin.*
import de.simplyroba.kata.VendingMachine.LastAction.*
import java.math.BigDecimal
import kotlin.text.StringBuilder

/**
 * @author simplyroba
 */
class VendingMachine {

    private val currentInsertedCoins: MutableList<Coin> = ArrayList()
    private val coinReturn: MutableList<InsertionObject> = ArrayList()
    private var lastAction: LastAction = NoAction

    companion object {
        const val PRICE = "PRICE"
        const val THANK_YOU = "THANK YOU"
        const val INSERT_COIN = "INSERT COIN"
        const val CURRENT_AMOUNT = "CURRENT AMOUNT"
        const val COIN_RETURN = "COIN RETURN"
    }

    fun insertObject(insertedObject: InsertionObject) {
        val coin = insertedObject.toCoin()
        if (coin != null) {
            currentInsertedCoins.add(coin)
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

        when (val lastAction = lastAction) {
            SuccessfulBuy -> sb.appendLine(THANK_YOU)
            is CurrentAmountNotEnough -> {
                sb
                    .appendLine(INSERT_COIN)
                    .append(PRICE).append(": ")
                    .appendLine(lastAction.neededAmount.toString())
            }
            else -> {
                if (currentInsertedCoins.isEmpty()) {
                    sb.appendLine(INSERT_COIN)
                } else {
                    sb
                        .append(CURRENT_AMOUNT).append(": ")
                        .append(currentAmount())
                        .appendLine(" $")
                }

                if (coinReturn.isNotEmpty()) {
                    sb
                        .append(COIN_RETURN).append(": ")
                        coinReturn.forEach { it ->
                            when (val coin = it.toCoin()) {
                                NICKEL, DIME, QUARTER -> sb.append(coin)
                                null -> sb.append(it.toString())
                            }
                        }
                    sb.appendLine()
                    coinReturn.clear()
                }
            }
        }

        lastAction.reset()

        return sb.toString();
    }

    fun buyProductAtLocation(location: String) {
        val product = Product.getProductByLocation(location)
        if (currentAmount() >= product.price) {
            currentInsertedCoins.clear()
            lastAction = SuccessfulBuy
        } else {
            lastAction = CurrentAmountNotEnough(product.price)
        }
    }

    sealed class LastAction {
        object NoAction: LastAction()
        object SuccessfulBuy: LastAction()
        data class CurrentAmountNotEnough(val neededAmount: BigDecimal): LastAction()
    }

    private fun LastAction.reset() {
        lastAction = NoAction
    }
}

data class InsertionObject(
    private val weightInGram: Float,
    private val diameterInMillimeter: Float) {

    fun toCoin(): Coin? {
        return try {
            Coin.getCoinByWeightAndDiameter(weightInGram, diameterInMillimeter)
        } catch (ex: NoSuchElementException) {
            null
        }
    }

    fun getValue(): BigDecimal {
        return toCoin()?.value ?: BigDecimal.ZERO
    }
}
