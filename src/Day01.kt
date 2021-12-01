fun main() {
    fun part1(input: List<String>): Int {
        var previousMeasurement = input.first().toInt()

        return input.fold(0) { total, current ->
            val measurement = current.toInt()
            val increase = if (measurement > previousMeasurement) total + 1 else total

            previousMeasurement = measurement

            increase
        }
    }

    fun part2(input: List<String>): Int {
        val windows = input
            .toInts()
            .windowed(3, 1)
        var previousWindowSize = windows.first().sum()

        return windows.fold(0) { total, window ->
            val windowSum = window.sum()
            val increase = if (windowSum > previousWindowSize) total + 1 else total

            previousWindowSize = windowSum

            increase
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)

    val input = readInput("Day01")
    val part1Result = part1(input)
    println("Part1: $part1Result")

    check(part2(testInput) == 5)

    val part2Result = part2(input)
    println("Part2: $part2Result")
}
