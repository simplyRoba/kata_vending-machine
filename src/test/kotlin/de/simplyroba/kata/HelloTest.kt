package de.simplyroba.kata

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

/**
 * @author simplyroba
 */
class HelloTest {

    @Test
    fun `should say hello`() {
        assertThat(hello()).contains("Hello")
    }
}
