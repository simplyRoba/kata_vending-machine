package de.simplyroba.kata

import com.google.common.truth.Truth.assertThat
import de.simplyroba.kata.Coin.*
import org.junit.jupiter.api.Test
import java.math.BigDecimal

/**
 * @author simplyroba
 */
class VendingMachineTest {

    private val vendingMachine = VendingMachine()

    @Test
    fun `should accept a valid coin`() {
        vendingMachine.insertObject(NICKEL.toInsertionObject())

        assertThat(vendingMachine.currentAmount()).isEqualTo(NICKEL.value)
    }

    @Test
    fun `should add value of valid coin to current amount`() {
        vendingMachine.insertObject(NICKEL.toInsertionObject())
        vendingMachine.insertObject(NICKEL.toInsertionObject())
        vendingMachine.insertObject(NICKEL.toInsertionObject())
        vendingMachine.insertObject(DIME.toInsertionObject())
        vendingMachine.insertObject(DIME.toInsertionObject())
        vendingMachine.insertObject(QUARTER.toInsertionObject())

        assertThat(vendingMachine.currentAmount()).isEqualTo(
            (NICKEL.value.multiply(3.toBigDecimal()))
                    + (DIME.value.multiply(2.toBigDecimal()))
                    + (QUARTER.value))
    }

    private fun Coin.toInsertionObject(): InsertionObject {
        return InsertionObject(weightInGram, diameterInMillimeter)
    }
}
