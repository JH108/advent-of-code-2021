package utils.day03

import utils.isOne
import utils.isZero

data class BitCounts(val zero: Int = 0, val one: Int = 0) {
    val moreOnesThanZeros get() = one > zero
    val moreZerosThanOnes get() = zero > one
    val fewerOnesThanZeros get() = one < zero
    val fewerZerosThanOnes get() = zero < one
    val isATie get() = one == zero

    companion object {
        val INITIAL = BitCounts(0, 0)
    }
}

fun BitCounts.increment(bitIsZero: Boolean) = if (bitIsZero) {
    this.copy(zero = this.zero + 1)
} else {
    this.copy(one = this.one + 1)
}

fun calculatePowerConsumption(gamma: Int, epsilon: Int): Int {
    return gamma * epsilon
}

class BinaryGrid(input: List<String>) {
    fun solveForOxygen(input: List<String>): Int {
        var remainingEntries = input
        val entrySize = input.first().length
        var bitIndex = 0

        while (bitIndex < entrySize && remainingEntries.size > 1) {
            val bitCounts = getBitCountsFromDiagnostic(remainingEntries)
            // go from 12 to 7 to 4 to 3 to 2 to 1
            val currentBitCount = bitCounts[bitIndex] ?: throw Exception("No bit count for this index")

            remainingEntries = remainingEntries.filter { entry ->
                val bit = entry[bitIndex]
                // Conditions to keep
                val isATieAndOne = currentBitCount.isATie && bit.isOne()
                val moreOnesAndOne = currentBitCount.moreOnesThanZeros && bit.isOne()
                val moreZerosAndZero = currentBitCount.moreZerosThanOnes && bit.isZero()

                isATieAndOne || moreOnesAndOne || moreZerosAndZero
            }

            bitIndex += 1
        }

        return remainingEntries.first().toInt(2)
    }

    fun solveForCo2(input: List<String>): Int {
        var remainingEntries = input
        val entrySize = input.first().length
        var bitIndex = 0

        while (bitIndex < entrySize && remainingEntries.size > 1) {
            val bitCounts = getBitCountsFromDiagnostic(remainingEntries)
            // go from 12 to 7 to 4 to 3 to 2 to 1
            val currentBitCount = bitCounts[bitIndex] ?: throw Exception("No bit count for this index")

            remainingEntries = remainingEntries.filter { entry ->
                val bit = entry[bitIndex]
                // Conditions to keep
                val isATieAndOne = currentBitCount.isATie && bit.isZero()
                val fewerOnesAndOne = currentBitCount.fewerOnesThanZeros && bit.isOne()
                val fewerZerosAndZero = currentBitCount.fewerZerosThanOnes && bit.isZero()

                isATieAndOne || fewerOnesAndOne || fewerZerosAndZero
            }

            bitIndex += 1
        }

        return remainingEntries.first().toInt(2)
    }

    fun part2(input: List<String>): Int {
        val oxygenRating = solveForOxygen(input)
        println("OXY: $oxygenRating")

        val co2Rating = solveForCo2(input)
        println("CO2: $co2Rating")

        return oxygenRating * co2Rating
    }

    private fun getBitCountsFromDiagnostic(report: List<String>): Map<Int, BitCounts> {
        var bitCounts = mapOf<Int, BitCounts>()

        report.forEach { binary ->
            binary.forEachIndexed { index, bit ->
                val currentCount = bitCounts[index] ?: BitCounts.INITIAL

                bitCounts = bitCounts + mapOf(
                    index to currentCount.increment(bit.isZero())
                )
            }
        }

        return bitCounts
    }

    fun part1(input: List<String>): Map<Int, BitCounts> {
        return getBitCountsFromDiagnostic(input)
    }
}

/**
 * Part2 Example
 */

// Oxygen Rating
//00100
//11110
//10110
//10111
//10101
//01111
//00111
//11100
//10000
//11001
//00010
//01010
// in column 1 there are 7 1s and 5 0s so we keep all 7 numbers starting with 1
//11110
//10110
//10111
//10101
//11100
//10000
//11001
// in column 2 there are 4 0s and 3 1s so we keep all 4 numbers starting with 0
//10110
//10111
//10101
//10000
// in column 3
//10111