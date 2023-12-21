import java.io.File
import java.io.FileReader
import kotlin.math.max

object Day2 {
     fun part1(filePath: String) {
        val file = File(filePath)
        val reader = FileReader(file)
        val sum: Int = reader.readLines().sumOf {
            checkLine(it)
        }
        println(sum)
    }

    fun checkLine(line: String): Int {
        val data = line.split("[:,;]".toRegex())
        val number = data[0].split(" ")[1].toInt()
        for (ball in data){
            val (count, colour) = ball.trimIndent().split(" ".toRegex())
            when (colour) {
                "red" -> if (count.toInt() > 12) return 0
                "green" -> if (count.toInt() > 13) return 0
                "blue" -> if (count.toInt() > 14) return 0
            }
        }
        return number
    }

    fun part2(filePath: String) {
        val file = File(filePath)
        val reader = FileReader(file)
        val sum: Int = reader.readLines().sumOf {
            checkLineSmallest(it)
        }
        println(sum)
    }

    fun checkLineSmallest(line: String): Int {
        val data = line.split("[:,;]".toRegex())
        var minGreen = 0
        var minBlue = 0
        var minRed = 0

        for (ball in data){
            val (count, colour) = ball.trimIndent().split(" ".toRegex())
            when (colour) {
                "red" -> {
                    minRed = max(minRed, count.toInt())
                }
                "green" -> {
                    minGreen = max(minGreen, count.toInt())
                }
                "blue" -> {
                    minBlue = max(minBlue, count.toInt())
                }
            }
        }
        return minGreen * minRed * minBlue
    }
}
fun main() {
    Day2.part2("src/main/resources/day1.txt")
}