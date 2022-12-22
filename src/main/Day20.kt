package day20

import utils.readInput

private const val filename = "Day20"

fun part1(filename: String): Long = readEncrypted(filename).mix().grooveCoordinates().sum()

fun readEncrypted(filename: String, key: Long = 1) = readInput(filename).map { it.toLong() * key }

fun part2(filename: String): Long =
    readEncrypted(filename, 811589153).mix(10).grooveCoordinates().sum()

fun List<Long>.grooveCoordinates(): List<Long> {
    val start = indexOf(0)
    return listOf(1000, 2000, 3000).map { this[(it + start) % size] }
}

fun List<Long>.mix(times: Int = 1): List<Long> =
    withIndex().toMutableList().apply { repeat(times) { mix() } }.map { it.value }

private fun MutableList<IndexedValue<Long>>.mix() {
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
