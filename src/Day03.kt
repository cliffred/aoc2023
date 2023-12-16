fun main() {
    solve(
        object : Challenge {
            override val day = 3

            override fun part1(input: List<String>): Long {
                val gridElements = input.toGridElements()
                val symbolPositions =
                    gridElements.filterIsInstance<Symbol>().map { it.position }.toSet()
                val numbers = gridElements.filterIsInstance<Number>()
                return numbers
                    .filter { it.positions.adjacentPositions().any { it in symbolPositions } }
                    .sumOf { it.value.toLong() }
            }

            override fun part2(input: List<String>): Long {
                val gridElements = input.toGridElements()
                val gears = gridElements.filterIsInstance<Symbol>().filter { it.value == '*' }
                val numbers = gridElements.filterIsInstance<Number>()
                return gears
                    .mapNotNull { symbol ->
                        val adjacentNumbers =
                            numbers.filter { symbol.position in it.positions.adjacentPositions() }
                        if (adjacentNumbers.size == 2)
                            adjacentNumbers[0].value * adjacentNumbers[1].value
                        else null
                    }
                    .sum()
                    .toLong()
            }

            override val test1 =
                Test(
                    input =
                        """
                467..114..
                ...*......
                ..35..633.
                ......#...
                617*......
                .....+.58.
                ..592.....
                ......755.
                ...#.*....
                .664.598..
            """
                            .trimIndent(),
                    expected = 4361
                )

            override val test2 = Test(input = test1.input, expected = 467835)
        }
    )
}

data class Position(val row: Int, val col: Int)

sealed interface GridElement

data class Symbol(val value: Char, val row: Int, val col: Int) : GridElement {
    val position = Position(row, col)
}

data class Number(val value: Int, val row: Int, val startCol: Int) : GridElement {
    val positions = (startCol until startCol + value.toString().length).map { Position(row, it) }
}

private fun List<String>.toGridElements() = buildSet {
    var currentNumber = ""
    var currentNumberStartCol = -1

    this@toGridElements.forEachIndexed { row, line ->
        fun storeNumber() {
            if (currentNumber.isNotEmpty()) {
                add(Number(currentNumber.toInt(), row, currentNumberStartCol))
                currentNumber = ""
                currentNumberStartCol = -1
            }
        }

        line.forEachIndexed { col, char ->
            when {
                char.isDigit() -> {
                    currentNumber += char
                    if (currentNumberStartCol == -1) currentNumberStartCol = col
                }
                else -> {
                    if (char != '.') add(Symbol(char, row, col))
                    storeNumber()
                }
            }
        }
        storeNumber()
    }
}

private fun Position.adjacentPositions() = buildSet {
    (-1..1).forEach { r ->
        (-1..1).forEach { c -> if (row != 0 || col != 0) add(Position(row + r, col + c)) }
    }
}

private fun Iterable<Position>.adjacentPositions() = buildSet {
    this@adjacentPositions.forEach { addAll(it.adjacentPositions()) }
}
