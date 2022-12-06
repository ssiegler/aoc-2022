package utils

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

/** Reads lines from the given input txt file. */
fun readInput(name: String): List<String> = Files.readAllLines(inputFile(name))

fun inputFile(name: String): Path = Paths.get("src", "input", "$name.txt")
