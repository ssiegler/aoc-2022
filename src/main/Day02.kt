package day02

import utils.readInput

enum class Shape {
    Rock,
    Paper,
    Scissors;

    val defeats: Shape
        get() =
            when (this) {
                Rock -> Scissors
                Paper -> Rock
                Scissors -> Paper
            }

    val defeated: Shape
        get() =
            when (this) {
                Rock -> Paper
                Paper -> Scissors
                Scissors -> Rock
            }

    fun defeats(other: Shape) = other == defeats

    fun score(): Int =
        when (this) {
            Rock -> 1
            Paper -> 2
            Scissors -> 3
        }
}

enum class Outcome {
    Lost,
    Draw,
    Won;

    fun score(): Int =
        when (this) {
            Lost -> 0
            Draw -> 3
            Won -> 6
        }
}

data class Round(val opponent: Shape, val selected: Shape) {
    private val outcome =
        when {
            selected == opponent -> Outcome.Draw
            selected.defeats(opponent) -> Outcome.Won
            else -> Outcome.Lost
        }

    val score = selected.score() + outcome.score()
}

fun Char.decodeOpponent(): Shape =
    when (this) {
        'A' -> Shape.Rock
        'B' -> Shape.Paper
        'C' -> Shape.Scissors
        else -> error("Cannot decode opponent move: $this")
    }

fun Char.decodeSuggestedMove(): Shape =
    when (this) {
        'X' -> Shape.Rock
        'Y' -> Shape.Paper
        'Z' -> Shape.Scissors
        else -> error("Cannot decode suggested move: $this")
    }

fun Char.decodeSuggestedOutcome(): Outcome =
    when (this) {
        'X' -> Outcome.Lost
        'Y' -> Outcome.Draw
        'Z' -> Outcome.Won
        else -> error("Cannot decode suggested outcome: $this")
    }

fun findShapeForOutcome(opponent: Shape, outcome: Outcome): Shape =
    when (outcome) {
        Outcome.Lost -> opponent.defeats
        Outcome.Draw -> opponent
        Outcome.Won -> opponent.defeated
    }

fun part1(filename: String): Int =
    readInput(filename)
        .map { Round(it.first().decodeOpponent(), it.last().decodeSuggestedMove()) }
        .sumOf(Round::score)

fun part2(filename: String) =
    readInput(filename)
        .map { it.first().decodeOpponent() to it.last().decodeSuggestedOutcome() }
        .map { (opponent, outcome) -> Round(opponent, findShapeForOutcome(opponent, outcome)) }
        .sumOf(Round::score)

fun main() {
    val filename = "Day02"
    println(part1(filename))
    println(part2(filename))
}
