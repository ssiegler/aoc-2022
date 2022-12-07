package day06

import utils.inputFile
import java.nio.file.Files

fun part1(input: String) = input.findMarker(4)

private fun String.findMarker(length: Int) =
    (windowed(length)
        .withIndex()
        .find { it.value.toSet().size == length }
        ?.let { it.index + length }
        ?: "Marker not found")

fun part2(input: String) = input.findMarker(14)

private const val filename = "Day06"

fun main() {
    val input = Files.readString(inputFile(filename))
    println(part1(input))
    println(part2(input))
}
