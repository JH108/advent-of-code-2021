package day05

class VentDetector(private val lines: List<Line>) {
    private val horizontalLines = lines.filter { it.horizontal }
    private val verticalLines = lines.filter { it.vertical }
    val diagonalLines = lines.filter { it.diagonal }

    fun plotVents() = plotHorizontalVents() + plotVerticalVents()

    fun findDangerousAreas(): List<Point> {
        val ventMap = plotVents()

        return ventMap.entries.flatMap { (x, line) ->
            line.mapIndexed { y, value -> if (value > 1) Point(x, y) else null}.filterNotNull()
        }
    }

    private fun plotHorizontalVents(): VentMap {
        val ventMap = lines.getVentMap().toMutableMap()

        horizontalLines.forEach { line ->
            val lineWidth = line.minX..line.maxX

            lineWidth.forEach { pointX ->
                val existingRow = ventMap[line.maxY] ?: throw Exception("No row for this line")
                val updatedRow = existingRow.toMutableList()

                updatedRow[pointX] = existingRow[pointX] + 1

                ventMap[line.maxY] = updatedRow
            }
        }

        return ventMap
    }

    private fun plotVerticalVents(): VentMap {
        val ventMap = lines.getVentMap().toMutableMap()

        verticalLines.forEach { line ->
            val lineHeight = line.minY..line.maxY

            lineHeight.forEach { pointY ->
                val existingRow = ventMap[pointY] ?: throw Exception("No row for this line")
                val updatedRow = existingRow.toMutableList()

                updatedRow[line.minX] = existingRow[line.minX] + 1

                ventMap[pointY] = updatedRow
            }
        }

        return ventMap
    }

    companion object {
        fun fromCoordinates(coordinates: List<String>): VentDetector {
            return VentDetector(coordinates.toLines())
        }

        private fun List<String>.toLines() = this.map { Line.fromString(it) }
    }

    private operator fun VentMap.plus(otherVentMap: VentMap): VentMap {
        val originalMap = this

        return buildMap {
            originalMap.entries.forEach { (rowIndex, firstLine) ->
                val secondLine = otherVentMap[rowIndex] ?: listOf()

                val combinedLines = firstLine.mapIndexed { columnIndex, point ->
                    val secondPoint = secondLine[columnIndex]

                    point + secondPoint
                }

                put(rowIndex, combinedLines)
            }
        }
    }

    private fun List<Line>.getVentMap(): Map<Int, List<Int>> {
        val startPoint = this.findTopLeftPoint()
        val endPoint = this.findBottomRightPoint()
        println("startPoint: $startPoint")
        println("endPoint: $endPoint")

        val height = 0..endPoint.y
        val width = 0..endPoint.x

        return buildMap {
            val row = buildList { addAll(width.map { 0 }) }

            height.forEach { line ->
                put(line, row)
            }
        }
    }

    private fun List<Line>.findTopLeftPoint(): Point {
        val topLeftXLine = this.minByOrNull { it.minX } ?: throw Exception("No line found")
        val topLeftYLine = this.minByOrNull { it.minY } ?: throw Exception("No line found")

        val topLeftX = topLeftXLine.minX
        val topLeftY = topLeftYLine.minY

        return Point(topLeftX, topLeftY)
    }

    private fun List<Line>.findBottomRightPoint(): Point {
        val topLeftXLine = this.maxByOrNull { it.maxX } ?: throw Exception("No line found")
        val topLeftYLine = this.maxByOrNull { it.maxY } ?: throw Exception("No line found")

        val topLeftX = topLeftXLine.maxX
        val topLeftY = topLeftYLine.maxY

        return Point(topLeftX, topLeftY)
    }
}

data class Line(val start: Point, val end: Point) {
    val horizontal = start.y == end.y
    val vertical = start.x == end.x
    val diagonal = horizontal.not() && vertical.not()
    val minX = if (start.x > end.x) end.x else start.x
    val minY = if (start.y > end.y) end.y else start.y
    val maxX = if (start.x < end.x) end.x else start.x
    val maxY = if (start.y < end.y) end.y else start.y

    companion object {
        fun fromString(line: String): Line {
            val start = line.substringBefore(LINE_DELIMITER)
            val end = line.substringAfter(LINE_DELIMITER)

            return Line(Point.fromString(start), Point.fromString(end))
        }

        private const val LINE_DELIMITER = " -> "
    }
}

data class Point(val x: Int, val y: Int) {
    companion object {
        fun fromString(point: String): Point {
            val x = point.substringBefore(POINT_DELIMITER).toInt()
            val y = point.substringAfter(POINT_DELIMITER).toInt()

            return Point(x, y)
        }

        private const val POINT_DELIMITER = ","
    }
}

typealias VentMap = Map<Int, List<Int>>

fun VentMap.print() {
    val width = this.values.first().size
    val barWidth = (width * 2) - 1

    println("| ${"-".repeat(barWidth)} |")

    this.values.forEach { row ->
        val contents = row.joinToString(" ") { if (it == 0) "." else it.toString() }
        println("| $contents |")
    }

    println("| ${"-".repeat(barWidth)} |")
}
