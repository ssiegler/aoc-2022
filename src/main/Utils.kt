package utils

import java.nio.file.Files
import java.nio.file.Paths

/** Reads lines from the given input txt file. */
fun readInput(name: String): List<String> =
    Files.readAllLines(Paths.get("src", "input", "$name.txt"))
