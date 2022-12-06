package day06

import utils.inputFile
import java.nio.file.Files

fun part1(input: String) =
    input.windowed(4).withIndex().find { it.value.toSet().size == 4 }?.let { it.index + 4 }
        ?: "Marker not found"

private const val filename = "Day06"

fun main() {
    val input = Files.readString(inputFile(filename))
    println(part1(input))
}
