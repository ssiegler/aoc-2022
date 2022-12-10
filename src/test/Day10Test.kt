import day10.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import utils.readInput
import kotlin.test.assertEquals

private const val filename = "Day10Example"

class Day10Test {
    @Test
    fun `reads program`() {
        assertEquals(
            listOf(Instruction.Noop, Instruction.Addx(3), Instruction.Addx(-5)),
            """
            noop
            addx 3
            addx -5
        """
                .trimIndent()
                .lines()
                .readProgram()
        )
    }

    @Test
    fun `executes program`() {
        assertEquals(
            listOf(1, 1, 4, 4, -1),
            Cpu()
                .execute(listOf(Instruction.Noop, Instruction.Addx(3), Instruction.Addx(-5)))
                .drop(2) // start and end of 0 cycle
        )
    }

    private val largerExampleProgram = readInput(filename).readProgram()
    private val values = Cpu().execute(largerExampleProgram)

    @ParameterizedTest
    @CsvSource("20, 21", "60, 19", "100, 18", "140, 21", "180,16", "220,18")
    fun `During the nth cycle, register X has the value v`(n: Int, v: Int) {
        assertEquals(v, values[n])
    }

    @Test
    fun `interesting signal strengths`() {
        assertEquals(
            listOf(420, 1140, 1800, 2940, 2880, 3960),
            largerExampleProgram
                .signalStrengths()
                .filterIndexed { index, _ -> index in 20..220 step 40 }
                .toList()
        )
    }

    @Test
    fun part1() {
        assertEquals(13140, day10.part1(filename))
    }
}
