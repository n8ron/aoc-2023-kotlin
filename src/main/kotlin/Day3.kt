import java.io.File
import java.io.FileReader

object Day3 {

    private val deltas = listOf(
        -1 to 0, -1 to 1, -1 to -1,
        1 to 0, 1 to 1, 1 to -1,
        0 to 1, 0 to -1
    )
    fun part1(filePath: String) {
        val file = File(filePath)
        val reader = FileReader(file)
        val lines = reader.readLines()
        val n = lines.size
        val m = lines[0].length
        var count = 0
        for ((i, line) in lines.withIndex()) {
            var startedDigit = false
            var accumulatedNumber = 0
            var isPartNumber = false
            for ((j, char) in line.withIndex()) {
                if (char.isDigit()) {
                    accumulatedNumber = accumulatedNumber * 10 + char.digitToInt()
                    if (!startedDigit) {
                        startedDigit = true
                    }
                    for ((dx, dy) in deltas) {
                        if (i + dx in 0..< n && j + dy in 0..< m) {
                            val ch = lines[i + dx][j + dy]
                            if (!ch.isDigit() && ch != '.') {
                                isPartNumber = true
                            }
                        }
                    }
                } else {
                    if (startedDigit) {
                        if (isPartNumber) {
                            count += accumulatedNumber
                        }
                        startedDigit = false
                        accumulatedNumber = 0
                        isPartNumber = false
                    }
                }

            }
            if (startedDigit && isPartNumber) {
                count += accumulatedNumber
            }

        }
        println(count)
    }
}

fun main() {
    Day3.part1("src/main/resources/day1.txt")

}