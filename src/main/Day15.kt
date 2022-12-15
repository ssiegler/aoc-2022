package day15

import geometry.Position
import geometry.manhattenDistance
import geometry.x
import utils.readInput

data class Sensor(val position: Position, val beacon: Position) {
    private val radius = beacon.manhattenDistance(position)
    val left = position.x - radius
    val right = position.x + radius

    fun detected(other: Position): Boolean {
        return other != beacon && other.manhattenDistance(position) <= radius
    }
}

fun String.toSensor(): Sensor {
    val (xS, yS, xB, yB) =
        "Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)"
            .toRegex()
            .matchEntire(this)
            ?.groupValues
            ?.drop(1)
            ?.map(String::toInt)
            ?: error("Invalid sensor: $this")

    return Sensor(xS to yS, xB to yB)
}

fun List<Sensor>.detected(y: Int): Int =
    (minOf { it.left }..maxOf { it.right }).count { x -> any { sensor -> sensor.detected(x to y) } }

fun part1(filename: String): Int = countCovered(filename, 2000000)

fun countCovered(filename: String, y: Int) = readInput(filename).map(String::toSensor).detected(y)

private const val filename = "Day15"

fun main() {
    println(part1(filename))
}
