package kata

import org.junit.Test
import kotlin.test.assertEquals

class ExampleTests {
    private fun runTest(puzzle: String, sol: String) = assertEquals(sol, Cryptarithm.alphametics(puzzle))

    @Test
    fun sendMoreMoney() {
        runTest("SEND + MORE = MONEY", "9567 + 1085 = 10652")
    }

    @Test
    fun zeroesOnes() {
        runTest("ZEROES + ONES = BINARY", "698392 + 3192 = 701584")
    }

    @Test
    fun doYouFeelLucky() {
        runTest("DO + YOU + FEEL = LUCKY", "57 + 870 + 9441 = 10368")
    }

    @Test
    fun coupleCouple() {
        runTest("COUPLE + COUPLE = QUARTET", "653924 + 653924 = 1307848")
    }

    @Test
    fun elevenNine() {
        runTest("ELEVEN + NINE + FIVE + FIVE = THIRTY", "797275 + 5057 + 4027 + 4027 = 810386")
    }

    @Test
    fun longTest() {
        runTest("KGKK + WMWDO + EMCKK + XEOGTEDE + KKMCWT + WKEKET + KEWCEW = XOMTDWMG", "4544 + 92903 + 12644 + 81357101 + 442697 + 941417 + 419619 = 83270925")
    }

}
