package de.simplyroba.kata

import com.google.common.truth.Truth.assertThat
import de.simplyroba.kata.Coin.*
import de.simplyroba.kata.Product.CANDY
import de.simplyroba.kata.Product.COLA
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

        @Test
        fun `should reset the display after a successful buy`() {
            vendingMachine.insertObject(NICKEL.toInsertionObject())
            vendingMachine.insertObject(DIME.toInsertionObject())
            vendingMachine.insertObject(QUARTER.toInsertionObject())
            vendingMachine.insertObject(QUARTER.toInsertionObject())

            vendingMachine.buyProductAtLocation(CANDY.location)

            assertThat(vendingMachine.display()).contains(VendingMachine.THANK_YOU)
            assertThat(vendingMachine.display()).contains(VendingMachine.INSERT_COIN)
        }

        @Test
        fun `should display price if not enough money was inserted for selected product`() {
            vendingMachine.insertObject(NICKEL.toInsertionObject())
            vendingMachine.insertObject(DIME.toInsertionObject())

            vendingMachine.buyProductAtLocation(COLA.location)

            val display = vendingMachine.display()
            assertThat(display).contains(VendingMachine.PRICE)
            assertThat(display).contains(COLA.price.toString())
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

    @Nested
    inner class SelectProduct {
        @Test
        fun `should reset amount after successful buy with exact amount`() {
            vendingMachine.insertObject(NICKEL.toInsertionObject())
            vendingMachine.insertObject(DIME.toInsertionObject())
            vendingMachine.insertObject(QUARTER.toInsertionObject())
            vendingMachine.insertObject(QUARTER.toInsertionObject())

            vendingMachine.buyProductAtLocation(CANDY.location)

            assertThat(vendingMachine.currentAmount()).isEqualTo(BigDecimal.ZERO)
        }
    }

    @Nested
    inner class MakeChange {
        @Test
        fun `should return coins that are above product price`() {
            vendingMachine.insertObject(DIME.toInsertionObject())
            vendingMachine.insertObject(QUARTER.toInsertionObject())
            vendingMachine.insertObject(QUARTER.toInsertionObject())
            vendingMachine.insertObject(QUARTER.toInsertionObject())
            vendingMachine.insertObject(QUARTER.toInsertionObject())

            vendingMachine.buyProductAtLocation(COLA.location)

            assertThat(vendingMachine.objectsInCoinReturn()).containsExactly(DIME.toInsertionObject())
        }
    }

    private fun Coin.toInsertionObject(): InsertionObject {
        return InsertionObject(weightInGram, diameterInMillimeter)
    }
}
