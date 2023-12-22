import java.io.File
import java.io.FileReader
import kotlin.math.max

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

    fun part2(filePath: String) {
        val file = File(filePath)
        val reader = FileReader(file)
        val lines = reader.readLines()
        val n = lines.size
        val m = lines[0].length
        val numberCount = MutableList(n) { MutableList(m) { 0 } }
        val numberProd = MutableList(n) { MutableList(m) { 1 } }
        val indexOfEnd =  MutableList(n) { MutableList(m) { mutableSetOf<Int>() } }
        for ((i, line) in lines.withIndex()) {
            var startedDigit = false
            var accumulatedNumber = 0
            var numberLen = 0
            for ((j, char) in line.withIndex()) {
                if (char.isDigit()) {
                    accumulatedNumber = accumulatedNumber * 10 + char.digitToInt()
                    numberLen += 1
                    if (!startedDigit) {
                        startedDigit = true
                    }
                } else {
                    if (startedDigit) {
                        processNumber(
                            lineIndex = i,
                            number = accumulatedNumber,
                            endOfNumber = j - 1,
                            numberLen = numberLen,
                            data = lines,
                            indexOfEnd, numberCount, numberProd
                        )
                        numberLen = 0
                        startedDigit = false
                        accumulatedNumber = 0
                    }
                }
                if (j == m - 1 && startedDigit) {
                    processNumber(
                        lineIndex = i,
                        number = accumulatedNumber,
                        endOfNumber = j,
                        numberLen = numberLen,
                        data = lines,
                        indexOfEnd, numberCount, numberProd
                    )
                }

            }

        }
        var resultSum = 0
        for (i in numberCount.indices) {
            for (j in numberCount[0].indices) {
                if (numberCount[i][j] == 2) {
                    resultSum += numberProd[i][j]
                }
            }
        }
        println(resultSum)
    }

    private fun processNumber(lineIndex: Int,
                              number: Int,
                              endOfNumber: Int,
                              numberLen: Int,
                              data: List<String>,
                              indexOfEnd: MutableList<MutableList<MutableSet<Int>>>,
                              numberCount: MutableList<MutableList<Int>>,
                              numberProd: MutableList<MutableList<Int>>
    ) {
        val n = data.size
        val m = data[0].length
        // println((endOfNumber - numberLen).. endOfNumber)
        for (currentDigitPos in (endOfNumber - numberLen + 1).. endOfNumber) {
            for ((dx, dy) in deltas) {
                if (lineIndex + dx in 0..< n && currentDigitPos + dy in 0..< m) {
                    val ch = data[lineIndex + dx][currentDigitPos + dy]
                    if (ch == '*') {
                        if (!indexOfEnd[lineIndex + dx][currentDigitPos + dy].contains(lineIndex * n + endOfNumber)) {
//                            print("Inc ")
//                            println(number)
//                            println("${lineIndex + dx} ${currentDigitPos + dy} | ${lineIndex} ${currentDigitPos}")
                            indexOfEnd[lineIndex + dx][currentDigitPos + dy].add(lineIndex * n + endOfNumber)
                            numberCount[lineIndex + dx][currentDigitPos + dy] += 1
                            numberProd[lineIndex + dx][currentDigitPos + dy] *= number
                        }
                    }
                }
            }
        }
    }
}

fun main() {
    Day3.part2("src/main/resources/day1.txt")

}