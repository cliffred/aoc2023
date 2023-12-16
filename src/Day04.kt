import java.util.LinkedList
import kotlin.math.pow

fun main() {
    solve(
        object : Challenge {
            override val day = 4

            override fun part1(input: List<String>): Long {
                val cards = input.toCards()
                return cards.sumOf { card ->
                    val matches = card.winners.size
                    if (matches == 0) 0 else 2.0.pow(matches.toDouble() - 1.0).toLong()
                }
            }

            override fun part2(input: List<String>): Long {
                val cards = LinkedList(input.toCards())
                val cardsById = cards.associateBy { it.id }

                val originals = cards.size
                var copies = 0L

                while (cards.isNotEmpty()) {
                    val card = cards.pop()
                    val nextCardId = card.id + 1
                    repeat(card.winners.size) { i ->
                        cardsById[nextCardId + i]?.let {
                            cards.addFirst(it)
                            copies++
                        }
                    }
                }
                return originals + copies
            }

            override val test1 =
                Test(
                    input =
                        """
                Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
                Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
                Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
                Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
                Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
                Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
            """
                            .trimIndent(),
                    expected = 13
                )

            override val test2 = Test(input = test1.input, expected = 30)
        }
    )
}

data class Card(val id: Int, val winningNumbers: Set<Int>, val numbers: Set<Int>) {
    val winners = numbers.intersect(winningNumbers)
}

private fun List<String>.toCards(): List<Card> {
    val whitespace = Regex("\\s+")
    return this.map { line ->
        val (cardId, content) = line.split(":")
        val id = cardId.substringAfterLast(' ')
        val (winningPart, numbersPart) = content.split("|")
        val winners = winningPart.trim().split(whitespace).map { it.toInt() }
        val numbers = numbersPart.trim().split(whitespace).map { it.toInt() }
        Card(id.toInt(), winners.toSet(), numbers.toSet())
    }
}
