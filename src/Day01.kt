fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { "${it.first { it.isDigit() }}${it.last { it.isDigit() }}".toInt() }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val occurrences = DIGITS.flatMap { (digit, integer) ->
                val firstIndex = line.indexOf(digit)
                val lastIndex = line.lastIndexOf(digit)
                listOf(firstIndex to integer, lastIndex to integer)
            }.toMap().filter { it.key >= 0 }

            val first = occurrences.minBy { it.key }.value
            val last = occurrences.maxBy { it.key }.value
            "${first}${last}".toInt()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    val testInput2 = readInput("Day01_test2")
    check(142) { part1(testInput) }
    check(281) { part2(testInput2) }

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

val DIGITS = mapOf(
    "1" to 1,
    "2" to 2,
    "3" to 3,
    "4" to 4,
    "5" to 5,
    "6" to 6,
    "7" to 7,
    "8" to 8,
    "9" to 9,
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9
)