package io.embrace.opentelemetry.kotlin.creator

/**
 * Utility functions for hex string validation.
 */
internal object HexUtils {

    /**
     * Returns true if the character is a valid hexadecimal digit (0-9, a-f, A-F).
     */
    fun Char.isHexDigit(): Boolean {
        return this.isDigit() || this in 'a'..'f' || this in 'A'..'F'
    }

    /**
     * Returns true if the string contains only valid hexadecimal characters.
     */
    fun String.isValidHex(): Boolean {
        return this.all { it.isHexDigit() }
    }
}
