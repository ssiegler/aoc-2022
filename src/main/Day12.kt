package day12

import geometry.Direction
import geometry.Position
import geometry.move
import utils.characterPositions
import utils.readInput
import java.util.*

typealias Height = UByte

data class HeightMap(
    private val heights: Map<Position, Height>,
    private val start: Position,
    private val target: Position,
) {
    private fun Position.neighbors(): List<Position> =
        Direction.values()
            .map(::move)
            .filter { heights[it] != null }
            .filter { heights[it]!! + 1u >= heights[this]!! }

    fun shortestPathLength(): Int =
        calculateDistances(target)[start] ?: error("No path to target found")

    fun shortestPathLengthFromAnywhere(): Int =
        calculateDistances(target)
            .filter { (position, _) -> heights[position]!! == 0.toUByte() }
            .minBy { (_, distance) -> distance }
            .value

    private fun calculateDistances(position: Position): Map<Position, Int> {
        val distances = mutableMapOf(position to 0)
        val boundary =
            PriorityQueue<Position>(Comparator.comparing { distances[it] ?: Int.MAX_VALUE })
        boundary.add(position)
        while (boundary.isNotEmpty()) {
            val next = boundary.remove()
            val distance = distances[next]!! + 1
            for (neighbor in next.neighbors().filter { distances[it] == null }) {
                distances[neighbor] = distance
                boundary.add(neighbor)
            }
        }
        return distances
    }
}

fun Char.toHeight(): Height =
    when (this) {
        in 'a'..'z' -> minus('a').toUByte()
        'S' -> 0u
        'E' -> 25u
        else -> error("Unknown height for marker '$this'")
    }

fun List<String>.readHeightMap(): HeightMap {
    val characterPositions = characterPositions
    return HeightMap(
        characterPositions.mapValues { (_, value) -> value.toHeight() },
        characterPositions.entries.find { it.value == 'S' }?.key ?: error("start point not found"),
        characterPositions.entries.find { it.value == 'E' }?.key ?: error("end point not found"),
    )
}

fun part1(filename: String): Int = readInput(filename).readHeightMap().shortestPathLength()

fun part2(filename: String): Int =
    readInput(filename).readHeightMap().shortestPathLengthFromAnywhere()

private const val filename = "Day12"

fun main() {
    println(part1(filename))
    println(part2(filename))
}
