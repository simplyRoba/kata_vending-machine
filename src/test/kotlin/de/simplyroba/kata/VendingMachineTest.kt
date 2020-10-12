package de.simplyroba.kata

import com.google.common.truth.Truth.assertThat
import de.simplyroba.kata.Coin.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.math.BigDecimal

/**
 * @author simplyroba
 */
class VendingMachineTest {

    private val vendingMachine = VendingMachine()

    @Nested
    inner class DisplayTests {
        @Test
        fun `displays INSERT COIN when no coin is inserted`() {
            assertThat(vendingMachine.display()).contains(VendingMachine.INSERT_COIN)
        }

        @Test
        fun `should show current amount`() {
            vendingMachine.insertObject(NICKEL.toInsertionObject())

            val display = vendingMachine.display()
            assertThat(display).contains(VendingMachine.CURRENT_AMOUNT)
            assertThat(display).contains("0.05")
        }
    }

    @Nested
    inner class AcceptCoins {
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

        @Test
        fun `should return invalid coins`() {
            val insertedObject1 = InsertionObject(200f, .22f)
            val insertedObject2 = InsertionObject(.444f, 583f)
            vendingMachine.insertObject(insertedObject1)
            vendingMachine.insertObject(insertedObject2)

            assertThat(vendingMachine.objectsInCoinReturn()).containsExactly(insertedObject1, insertedObject2)
            assertThat(vendingMachine.currentAmount()).isEqualTo(BigDecimal.ZERO)
        }
    }

    private fun Coin.toInsertionObject(): InsertionObject {
        return InsertionObject(weightInGram, diameterInMillimeter)
    }
}
