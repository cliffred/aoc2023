fun main() {
    solve(
        object : Challenge {
            override val day = 2

            override fun part1(input: List<String>): Long {
                val maxRed = 12
                val maxGreen = 13
                val maxBlue = 14
                return input.sumOf { line ->
                    val (number, sets) = line.toGame()
                    if (
                        sets.any {
                            it.getOrDefault("red", 0) > maxRed ||
                                it.getOrDefault("green", 0) > maxGreen ||
                                it.getOrDefault("blue", 0) > maxBlue
                        }
                    ) {
                        0L
                    } else {
                        number
                    }
                }
            }

            override fun part2(input: List<String>): Long {
                return input.sumOf { line ->
                    val (_, sets) = line.toGame()
                    val minRed = sets.maxOf { it.getOrDefault("red", 0) }
                    val minGreen = sets.maxOf { it.getOrDefault("green", 0) }
                    val minBlue = sets.maxOf { it.getOrDefault("blue", 0) }
                    minRed.toLong() * minGreen * minBlue
                }
            }

            private fun String.toGame(): Pair<Long, List<Map<String, Int>>> {
                val (name, game) = split(": ")
                val number = name.split(" ")[1].toLong()
                val sets =
                    game.split("; ").map { set ->
                        set.split(", ").associate { cube ->
                            val (amount, color) = cube.split(" ")
                            color to amount.toInt()
                        }
                    }
                return Pair(number, sets)
            }

            override val test1 =
                Test(
                    input =
                        """
                Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
                Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
                Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
                Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
                Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
            """
                            .trimIndent(),
                    expected = 8
                )

            override val test2 = Test(input = test1.input, expected = 2286)
        }
    )
}
