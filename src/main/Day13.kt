package day13

import utils.readInput
import java.io.Reader
import java.io.StringReader

sealed interface Packet : Comparable<Packet> {
    data class Number(val value: Int) : Packet {
        override fun compareTo(other: Packet): Int =
            when (other) {
                is Number -> value.compareTo(other.value)
                is PacketList -> PacketList(listOf(this)).compareTo(other)
            }
    }

    data class PacketList(val items: List<Packet>) : Packet {

        constructor(vararg values: Int) : this(values.map(::Number))
        constructor(vararg values: Packet) : this(values.asList())

        override fun compareTo(other: Packet): Int =
            when (other) {
                is Number -> compareTo(PacketList(listOf(other)))
                is PacketList -> {
                    items
                        .zip(other.items)
                        .map { (left, right) -> left.compareTo(right) }
                        .dropWhile(0::equals)
                        .firstOrNull()
                        ?: items.size.compareTo(other.items.size)
                }
            }
    }
}

sealed interface Token {
    object LeftBracket : Token
    object RightBracket : Token
    object Comma : Token

    object EOF : Token
    data class Number(val value: Int) : Token
}

class Scanner(private val input: Reader) {
    private var code: Int = input.read()
    lateinit var token: Token

    fun advance() {
        token =
            when (code) {
                in Int.MIN_VALUE until 0 -> {
                    Token.EOF
                }
                in '0'.code..'9'.code -> {
                    var number: Int = code - '0'.code
                    readNext()
                    while (code in '0'.code..'9'.code) {
                        number = 10 * number + (code - '0'.code)
                        readNext()
                    }
                    Token.Number(number)
                }
                '['.code -> {
                    readNext()
                    Token.LeftBracket
                }
                ']'.code -> {
                    readNext()
                    Token.RightBracket
                }
                ','.code -> {
                    readNext()
                    Token.Comma
                }
                else -> {
                    error("Unknown character: $code")
                }
            }
    }

    private fun readNext() {
        code = input.read()
    }
}

fun List<String>.readPairs(): List<Pair<Packet, Packet>> =
    chunked(3).map { (first, second, _) -> first.readPacket() to second.readPacket() }

fun String.readPacket(): Packet = parse(Scanner(StringReader(this)).apply { advance() })

private fun parse(scanner: Scanner): Packet =
    when (val token = scanner.token) {
        is Token.Number -> Packet.Number(token.value)
        Token.LeftBracket -> parseList(scanner)
        Token.Comma,
        Token.RightBracket,
        Token.EOF -> error("Expected number or '[', got $token")
    }.also { scanner.advance() }

private fun parseList(scanner: Scanner): Packet.PacketList {
    scanner.advance()
    val list = mutableListOf<Packet>()
    if (scanner.token != Token.RightBracket) {
        list.add(parse(scanner))
        while (scanner.token == Token.Comma) {
            scanner.advance()
            list.add(parse(scanner))
        }
    }
    require(scanner.token == Token.RightBracket) { "Expected ']', got ${scanner.token}" }
    return Packet.PacketList(list)
}

fun List<String>.readPackets() = filter(String::isNotBlank).map(String::readPacket)

fun part1(filename: String) =
    readInput(filename)
        .readPairs()
        .withIndex()
        .filter { (_, value) -> value.first < value.second }
        .sumOf { (index, _) -> index + 1 }

val dividers =
    listOf(Packet.PacketList(Packet.PacketList(2)), Packet.PacketList(Packet.PacketList(6)))

fun part2(filename: String): Int {
    val sorted = (readInput(filename).readPackets() + dividers).sorted()
    return (sorted.findIndex(dividers[0])) * sorted.findIndex(dividers[1])
}

private fun List<Packet>.findIndex(divider: Packet.PacketList) = indexOf(divider) + 1

private const val filename = "Day13"

fun main() {
    println(part1(filename))
    println(part2(filename))
}
