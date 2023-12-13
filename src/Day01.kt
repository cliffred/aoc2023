fun main() {
    solve(
        object : Challenge {
            override val day = 1

            override fun part1(input: List<String>): Long {
                return input.sumOf {
                    "${it.first { it.isDigit() }}${it.last { it.isDigit() }}".toLong()
                }
            }

            override fun part2(input: List<String>): Long {
                return input.sumOf { line ->
                    val occurrences =
                        DIGITS.flatMap { (digit, integer) ->
                                val firstIndex = line.indexOf(digit)
                                val lastIndex = line.lastIndexOf(digit)
                                listOf(firstIndex to integer, lastIndex to integer)
                            }
                            .toMap()
                            .filter { it.key >= 0 }

                    val first = occurrences.minBy { it.key }.value
                    val last = occurrences.maxBy { it.key }.value
                    "${first}${last}".toLong()
                }
            }

            override val test1 =
                Test(
                    input =
                        """
                1abc2
                pqr3stu8vwx
                a1b2c3d4e5f
                treb7uchet
            """
                            .trimIndent(),
                    expected = 142
                )

            override val test2 =
                Test(
                    input =
                        """
                two1nine
                eightwothree
                abcone2threexyz
                xtwone3four
                4nineeightseven2
                zoneight234
                7pqrstsixteen
            """
                            .trimIndent(),
                    expected = 281
                )
        }
    )
}

val DIGITS =
    mapOf(
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
