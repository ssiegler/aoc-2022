import day16.readValves
import org.junit.jupiter.api.Test
import utils.readInput
import kotlin.test.assertEquals

private const val filename = "Day16Example"

class Day16Test {

    @Test
    fun `reads valves`() {
        val valves = readInput(filename).readValves()
        assertEquals(
            "Valves(flows={AA=0, BB=13, CC=2, DD=20, EE=3, FF=0, GG=0, HH=22, II=0, JJ=21}, tunnels={AA=[DD, II, BB], BB=[CC, AA], CC=[DD, BB], DD=[CC, AA, EE], EE=[FF, DD], FF=[EE, GG], GG=[FF, HH], HH=[GG], II=[AA, JJ], JJ=[II]})",
            valves.toString()
        )
    }

    @Test
    fun part1() {
        assertEquals(1651, day16.part1(filename))
    }
}
