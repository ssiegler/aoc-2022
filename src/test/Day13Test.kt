import day13.Packet
import day13.dividers
import day13.readPacket
import day13.readPackets
import org.junit.jupiter.api.Test
import utils.readInput
import kotlin.test.assertEquals
import kotlin.test.assertTrue

private const val filename = "Day13Example"

class Day13Test {
    @Test
    fun compareExamples() {
        assertTrue(Packet.PacketList(1, 1, 3, 1, 1) < Packet.PacketList(1, 1, 5, 1, 1))
        assertTrue("[[1],[2,3,4]]".readPacket() < "[[1],4]".readPacket())
    }

    @Test
    fun part1() {
        assertEquals(13, day13.part1(filename))
    }

    @Test
    fun `correct order`() {
        assertEquals(
            """
            []
            [[]]
            [[[]]]
            [1,1,3,1,1]
            [1,1,5,1,1]
            [[1],[2,3,4]]
            [1,[2,[3,[4,[5,6,0]]]],8,9]
            [1,[2,[3,[4,[5,6,7]]]],8,9]
            [[1],4]
            [[2]]
            [3]
            [[4,4],4,4]
            [[4,4],4,4,4]
            [[6]]
            [7,7,7]
            [7,7,7,7]
            [[8,7,6]]
            [9]
        """
                .trimIndent()
                .lines()
                .readPackets(),
            (readInput(filename).filter { it.isNotBlank() }.readPackets() + dividers).sorted()
        )
    }

    @Test
    fun part2() {
        assertEquals(140, day13.part2(filename))
    }
}
