package de.simplyroba.kata

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal

/**
 * @author simplyroba
 */
class VendingMachineTest {

    private val vendingMachine = VendingMachine()

    @Test
    fun `should accept a valid coin`() {
        vendingMachine.insertObject(InsertionObject(weightInGramm = 5f, diameterInMilimeter = 21.21f))

        assertThat(vendingMachine.currentAmount()).isEqualTo(BigDecimal("0.05"))
    }
}
