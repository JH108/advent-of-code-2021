package utils.day04

import utils.toInts
import kotlin.properties.Delegates

class SubmarineBingo(input: List<String>) {
    var calls = input.first().split(",").toInts()
    var finished = false
    var finalScore = 0
    var boards = input
        .drop(1)
        .filter { it.isNotEmpty() }
        .windowed(5, 5)
        .map { Board.parse(it) }
    var completedBoards: List<Board> = listOf()

    /**
     * Returns the sum of the rows in the winning board multiplied by the winning call
     * Returns -1 if there was no winning board
     */
    fun win(): Int {
        var call = 0

        while (finished.not()) {
            call = calls.first()
            calls = calls.drop(1)

            boards.forEach { board ->
                board.callNumber(call)

                if (board.finished) {
                    finalScore = board.finalScore
                    finished = true
                }
            }
        }
        return call * finalScore
    }

    fun lose(): Int {
        var call = 0

        while (finished.not()) {
            call = calls.first()
            calls = calls.drop(1)

            boards.forEach { board ->
                board.callNumber(call)

                if (board.finished) {
                    completedBoards = completedBoards + listOf(board)
                    boards = boards - board
                }
            }

            if (boards.isEmpty() || calls.isEmpty()) {
                finished = true
            }
        }

        return call * completedBoards.last().finalScore
    }
}

data class Call(val row: Int, val column: Int, val number: Int)

class Board {
    var grid: List<List<Int>> = listOf()
    var finalScore = 0
    var finished = false
    var winningRow: List<Call> = listOf()
    var winningColumn: List<Call> = listOf()

    var calls: List<Call> by Delegates.observable(listOf()) {_, _, value ->
        val row = value.groupBy { it.row }.filter {it.value.size == 5}.firstNotNullOfOrNull { it.value }
        val column = value.groupBy { it.column }.filter {it.value.size == 5}.firstNotNullOfOrNull { it.value }

        finished = when {
            row?.size == 5 -> {
                val uncheckedNumbers = grid.flatten().partition { number -> value.filter {it.number == number}.isEmpty() }.first
                finalScore = uncheckedNumbers.sum()
                winningRow = row

                true
            }
            column?.size == 5 -> {
                val uncheckedNumbers = grid.flatten().partition { number -> value.filter {it.number == number}.isEmpty() }.first
                finalScore = uncheckedNumbers.sum()
                winningColumn = column

                true
            }
            else -> {
                false
            }
        }
    }

    fun callNumber(call: Int) {
        for (rowIndex in grid.indices) {
            val row = grid[rowIndex]

            for (columnIndex in row.indices) {
                val number = row[columnIndex]

                if (call == number) {
                    calls = calls + listOf(
                        Call(
                            row = rowIndex,
                            column = columnIndex,
                            number = number
                        )
                    )
                }
            }
        }
    }

    companion object {
        /**
         * Expects a string formatted like: `["22 13 17 11  0", " 8  2 23  4 24", "21  9 14 16  7", " 6 10  3 18  5", " 1 12 20 15 19"]`
         */
        fun parse(input: List<String>): Board {
            val board = Board()

            board.grid = input.buildGrid()

            return board
        }

        private fun List<String>.buildGrid() = this.map { row ->
            row
                .split(" ")
                .filter { it.isNotEmpty() }
                .map { it.toInt() }
        }
    }

    override fun toString(): String {
        return grid.toString()
    }
}
