import java.io.File
import java.io.FileReader
import kotlin.math.pow

object Day4 {
    fun part1(filePath: String) {
        val file = File(filePath)
        val reader = FileReader(file)
        val totalCost = reader.readLines().sumOf {
            costLine(it)
        }
        println(totalCost)
    }

    private fun countWin(line: String): Int {
        val regex = Regex(".*:(.*)\\|(.*)")
        val match = regex.find(line)!!
        val (myNumbersStr, winNumbersStr) = match.destructured
        val myNumbers = myNumbersStr.split(" ").filter { it != ""}.toSet()
        val winNumbers = winNumbersStr.split(" ").filter { it != ""}.toSet()
        return myNumbers.intersect(winNumbers).size
    }

    private fun costLine(line: String): Int {
        return 2.0.pow(countWin(line) - 1).toInt()
    }

    fun part2(filePath: String) {
        val file = File(filePath)
        val reader = FileReader(file)
        val lines = reader.readLines()
        val countCards = MutableList(lines.size) { 1 }
        var totalCount = 0
        for ((i, line) in lines.withIndex()) {
            totalCount += countCards[i]
            val numberOfWins = countWin(line)
            for (j in i + 1 .. i + numberOfWins) {
                countCards[j] += countCards[i]
            }
        }
        println(totalCount)

    }
}

fun main() {
    Day4.part2("src/main/resources/day1.txt")

}