import kotlin.math.ceil
import kotlin.math.pow
import kotlin.math.sqrt

fun main() {
    solve(
        object : Challenge {
            override val day = 6

            override fun part1(input: List<String>): Long {
                val races: List<Race> = input.toRaces1()
                return races.fold(1L) { acc, race -> race.waysToWin() * acc }
            }

            override fun part2(input: List<String>): Long {
                val race: Race = input.toRace2()
                return race.waysToWin()
            }

            override val test1 =
                Test(
                    input =
                        """
                Time:      7  15   30
                Distance:  9  40  200
            """
                            .trimIndent(),
                    expected = 288
                )

            override val test2 = Test(input = test1.input, expected = 71503)
        }
    )
}

typealias Race = Pair<Long, Long>

private fun List<String>.toRaces1(): List<Race> {
    val (times, distances) = map { it.split("\\s+".toRegex()).drop(1).map { it.toLong() } }
    val races: List<Race> = times zip distances
    return races
}

private fun List<String>.toRace2(): Race {
    val (time, distance) = map { it.replace(" ", "").split(":")[1].toLong() }
    return time to distance
}

fun Race.waysToWin(): Long {
    val (duration, record) = this

    // x*(duration - x) > record
    // duration * x - x^2 > record
    // x^2 -duration*x + record+1 = 0
    // D=b^2−4ac | a=1, b=-duration, c=record+1
    val discriminant = duration.toDouble().pow(2) - 4 * (record + 1)
    val x1 = (duration - sqrt(discriminant)) / 2
    val x2 = (duration + sqrt(discriminant)) / 2
    return x2.toLong() - ceil(x1).toLong() + 1
}
