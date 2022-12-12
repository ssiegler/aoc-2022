package utils

import geometry.Position
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

/** Reads lines from the given input txt file. */
fun readInput(name: String): List<String> = Files.readAllLines(inputFile(name))

val List<String>.characterPositions: Map<Position, Char>
    get() =
        withIndex()
            .flatMap { (row, columns) ->
                columns.withIndex().map { (column, input) -> column to row to input }
            }
            .toMap()

fun inputFile(name: String): Path = Paths.get("src", "input", "$name.txt")
