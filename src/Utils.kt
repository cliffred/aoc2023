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
    println("Part 1: ${challenge.part1(input)}")

    assertEquals(challenge.test2.expected, challenge.part2(challenge.test2.input.lines()))
    println("Part 2: ${challenge.part2(input)}")
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
