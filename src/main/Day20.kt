package day20

import utils.readInput

private const val filename = "Day20"

fun part1(filename: String): Int =
    readInput(filename).map { it.toInt() }.mix().grooveCoordinates().sum()

fun part2(filename: String): Int = TODO()

fun List<Int>.grooveCoordinates(): List<Int> {
    val start = indexOf(0)
    return listOf(1000, 2000, 3000).map { this[(it + start) % size] }
}

fun List<Int>.mix(): List<Int> = withIndex().toMutableList().apply { mix() }.map { it.value }

private fun MutableList<IndexedValue<Int>>.mix() {
    indices.forEach { originalIndex ->
        val index = indexOfFirst { it.index == originalIndex }
        val element = removeAt(index)
        add((index + element.value).mod(size), element)
    }
}

fun main() {
    println(part1(filename))
    println(part2(filename))
}
