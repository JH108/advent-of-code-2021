package utils

import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

/**
 * Converts a List<String> into List<Int>
 */
fun List<String>.toInts() = this.map { it.toInt() }


const val UTF16_ZERO = 48
const val UTF16_ONE = 49

fun Char.isZero() = this.code == UTF16_ZERO

fun Char.isOne() = this.code == UTF16_ONE