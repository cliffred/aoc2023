fun main() {
    solve(
        object : Challenge {
            override val day = 5

            override fun part1(input: List<String>): Long {
                val almanac = input.toAlmanac()
                val seeds = almanac.seeds
                val locations =
                    seeds.map { seed -> almanac.maps.fold(seed) { result, map -> map[result] } }
                return locations.min().toLong()
            }

            override fun part2(input: List<String>): Long {
                val almanac = input.toAlmanac()
                val seedRanges =
                    almanac.seeds.chunked(2).map { (start, length) -> start ..< start + length }

                var location = 0L
                while (true) {
                    val seed =
                        almanac.maps.foldRight(location) { map, result -> map.getInverse(result) }
                    if (seedRanges.any { seed in it }) {
                        return location
                    }
                    location++
                }
            }

            override val test1 =
                Test(
                    input =
                        """
                seeds: 79 14 55 13

                seed-to-soil map:
                50 98 2
                52 50 48
                
                soil-to-fertilizer map:
                0 15 37
                37 52 2
                39 0 15
                
                fertilizer-to-water map:
                49 53 8
                0 11 42
                42 0 7
                57 7 4
                
                water-to-light map:
                88 18 7
                18 25 70
                
                light-to-temperature map:
                45 77 23
                81 45 19
                68 64 13
                
                temperature-to-humidity map:
                0 69 1
                1 0 69
                
                humidity-to-location map:
                60 56 37
                56 93 4
            """
                            .trimIndent(),
                    expected = 35
                )

            override val test2 = Test(input = test1.input, expected = 46)
        }
    )
}

data class Almanac(val seeds: List<Long>, val maps: List<RangeMap>)

data class RangeMap(val name: String, val entries: List<RangeEntry>) {
    operator fun get(index: Long): Long {
        val entry =
            entries.firstOrNull { index in it.sourceRangeStart..it.sourceRangeStart + it.length }
        val diff = entry?.let { it.destinationRangeStart - it.sourceRangeStart } ?: 0
        return index + diff
    }

    fun getInverse(index: Long): Long {
        val entry =
            entries.firstOrNull {
                index in it.destinationRangeStart..it.destinationRangeStart + it.length
            }
        val diff = entry?.let { it.sourceRangeStart - it.destinationRangeStart } ?: 0
        return index + diff
    }
}

data class RangeEntry(
    val destinationRangeStart: Long,
    val sourceRangeStart: Long,
    val length: Long
)

private fun List<String>.toAlmanac(): Almanac {
    val seeds = first().split(' ').drop(1).map { it.toLong() }

    val iterator = drop(2).iterator()
    val rangeMaps = mutableListOf<RangeMap>()
    while (iterator.hasNext()) {
        val line = iterator.next()
        if (line.contains("map:")) {
            val mapName = line.split(' ').first()
            val mapEntries = mutableListOf<RangeEntry>()
            while (iterator.hasNext()) {
                val mapLine = iterator.next()
                if (mapLine.isBlank()) {
                    break
                }
                mapEntries += mapLine.toRangeEntry()
            }
            rangeMaps += RangeMap(mapName, mapEntries)
        }
    }
    return Almanac(seeds, rangeMaps)
}

private fun String.toRangeEntry(): RangeEntry {
    val (destinationRangeStart, sourceRangeStart, length) = split(' ').map { it.toLong() }
    val rangeEntry = RangeEntry(destinationRangeStart, sourceRangeStart, length)
    return rangeEntry
}
