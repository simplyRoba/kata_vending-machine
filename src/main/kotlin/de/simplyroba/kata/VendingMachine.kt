package de.simplyroba.kata

import de.simplyroba.kata.Coin.NICKEL
import java.math.BigDecimal

/**
 * @author simplyroba
 */
class VendingMachine {
    fun insertObject(insertionObject: InsertionObject) {
        // validate coin
        // add value
        // add to current amount
    }

    fun currentAmount(): BigDecimal {
        return NICKEL.value
    }
}

class InsertionObject(
    val weightInGram: Float,
    val diameterInMillimeter: Float) {

}
