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
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)

    val input = readInput("Day01")
    val part1Result = part1(input)
    println("Part1: $part1Result")
    println(part2(input))
}
