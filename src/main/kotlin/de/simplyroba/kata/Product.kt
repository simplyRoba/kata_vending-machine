package de.simplyroba.kata

import java.math.BigDecimal

/**
 * @author simplyroba
 */
enum class Product(
    val location: String,
    val price: BigDecimal,
) {
    COLA("A1", BigDecimal("1")),
    CHIPS("A2", BigDecimal("0.5")),
    CANDY("A3", BigDecimal("0.65"));

    companion object {
        fun getProductByLocation(location: String): Product {
            return values()
                    .first { product -> product.location.equals(location, true) }
        }
    }
}
