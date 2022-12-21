package day18

import utils.readInput

fun readCubes(filename: String) =
    readInput(filename)
        .map {
            val (x, y, z) = it.split(",").map { it.toInt() }
            Triple(x, y, z)
        }
        .toSet()

fun Set<Triple<Int, Int, Int>>.surface(): Int =
    flatMap {
            listOf(
                it.copy(first = it.first - 1),
                it.copy(first = it.first + 1),
                it.copy(second = it.second - 1),
                it.copy(second = it.second + 1),
                it.copy(third = it.third - 1),
                it.copy(third = it.third + 1),
            )
        }
        .count { it !in this }

fun part1(filename: String) = readCubes(filename).surface()

private const val filename = "Day18"

fun main() {
    println(part1(filename))
}
