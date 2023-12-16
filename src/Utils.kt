import kotlin.time.measureTimedValue

interface Challenge {

    val day: Int

    fun part1(input: List<String>): Long

    fun part2(input: List<String>): Long

    val test1: Test

    val test2: Test
}

data class Test(val input: String, val expected: Long)

/** Solves the given challenge. */
fun solve(challenge: Challenge) {
    val input = readInput("Day${challenge.day.toString().padStart(2, '0')}")
    println("=== Day ${challenge.day} ===")

    assertEquals(challenge.test1.expected, challenge.part1(challenge.test1.input.lines()))
    val (part1, duration1) = measureTimedValue { challenge.part1(input) }
    println("Part 1: $part1 in ${duration1.inWholeMilliseconds}ms")

    assertEquals(challenge.test2.expected, challenge.part2(challenge.test2.input.lines()))
    val (part2, duration2) = measureTimedValue { challenge.part2(input) }
    println("Part 2: $part2 in ${duration2.inWholeMilliseconds}ms")
}

/** Assert that the given [expected] value equals the [actual] value. */
fun assertEquals(expected: Any?, actual: Any?) {
    if (expected != actual) {
        throw AssertionError("Expected $expected but was $actual")
    }
}

/** Reads lines from the given input txt file. */
private fun readInput(name: String) =
    Thread.currentThread()
        .contextClassLoader
        .getResourceAsStream("$name.txt")!!
        .bufferedReader()
        .readLines()
