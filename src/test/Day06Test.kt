package day06

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertEquals

class Day06Test {
    @ParameterizedTest
    @CsvSource(
        "bvwbjplbgvbhsrlpgdmjqwftvncz,       5",
        "nppdvjthqldpwncqszvftbrmjlhg,       6",
        "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg, 10",
        "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw,  11",
    )
    fun part1(input: String, result: Int) {
        assertEquals(result, part1(input))
    }

    @ParameterizedTest
    @CsvSource(
        "mjqjpqmgbljsphdztnvjfqwrcgsmlb,    19",
        "bvwbjplbgvbhsrlpgdmjqwftvncz,      23",
        "nppdvjthqldpwncqszvftbrmjlhg,      23",
        "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg, 29",
        "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw,  26",
    )
    fun part2(input: String, result: Int) {
        assertEquals(result, part2(input))
    }
}
