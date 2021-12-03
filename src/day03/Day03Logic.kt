package utils.day03

import utils.isOne
import utils.isZero

data class BitCount(val zero: Int = 0, val one: Int = 0) {
    val moreOnesThanZeros get() = one > zero
    val moreZerosThanOnes get() = zero > one
    val fewerOnesThanZeros get() = one < zero
    val fewerZerosThanOnes get() = zero < one
    val isATie get() = one == zero

    companion object {
        val INITIAL = BitCount(0, 0)
    }
}

fun BitCount.increment(bitIsZero: Boolean) = if (bitIsZero) {
    this.copy(zero = this.zero + 1)
} else {
    this.copy(one = this.one + 1)
}

private fun getBitCountsFromDiagnosticReport(input: List<String>): Map<Int, BitCount> {
    var bitCounts = mapOf<Int, BitCount>()

    input.forEach { binary ->
        binary.forEachIndexed { index, bit ->
            val currentCount = bitCounts[index] ?: BitCount.INITIAL

            bitCounts = bitCounts + mapOf(
                index to currentCount.increment(bit.isZero())
            )
        }
    }

    return bitCounts
}

sealed class DiagnosticRating(private val input: List<String>) {
    class PowerConsumption(val input: List<String>) : DiagnosticRating(input) {
        override fun calculate(): Int {
            val bitCounts = getBitCountsFromDiagnosticReport(input)

            return bitCounts.getGammaRating() * bitCounts.getEpsilonRating()
        }

        private fun Map<Int, BitCount>.getGammaRating() = this.entries
            .joinToString("") { (_, bitCount) ->
                if (bitCount.moreOnesThanZeros) "1" else "0"
            }.toInt(2)

        private fun Map<Int, BitCount>.getEpsilonRating() = this.entries
            .joinToString("") { (_, bitCount) ->
                if (bitCount.fewerOnesThanZeros) "1" else "0"
            }.toInt(2)
    }

    class OxygenRating(input: List<String>) : DiagnosticRating(input) {
        override fun shouldKeepEntry(bitCount: BitCount, bit: Char): Boolean {
            val isATieAndOne = bitCount.isATie && bit.isOne()
            val moreOnesAndOne = bitCount.moreOnesThanZeros && bit.isOne()
            val moreZerosAndZero = bitCount.moreZerosThanOnes && bit.isZero()

            return isATieAndOne || moreOnesAndOne || moreZerosAndZero
        }
    }

    class CO2Rating(input: List<String>) : DiagnosticRating(input) {
        override fun shouldKeepEntry(bitCount: BitCount, bit: Char): Boolean {
            val isATieAndOne = bitCount.isATie && bit.isZero()
            val fewerOnesAndOne = bitCount.fewerOnesThanZeros && bit.isOne()
            val fewerZerosAndZero = bitCount.fewerZerosThanOnes && bit.isZero()

         return isATieAndOne || fewerOnesAndOne || fewerZerosAndZero
        }
    }

    open fun shouldKeepEntry(bitCount: BitCount, bit: Char): Boolean {
        return bitCount.isATie && bit.isOne()
    }

    open fun calculate(): Int {
        var remainingEntries = input
        val entrySize = input.first().length
        var bitIndex = 0

        while (bitIndex < entrySize && remainingEntries.size > 1) {
            val bitCounts = getBitCountsFromDiagnosticReport(remainingEntries)
            val bitCount = bitCounts[bitIndex] ?: throw Exception("No bit count for this index")

            remainingEntries = remainingEntries.filter { entry ->
                val bit = entry[bitIndex]

                shouldKeepEntry(bitCount, bit)
            }

            bitIndex += 1
        }

        return remainingEntries.first().toInt(2)
    }
}
