import java.io.File
import java.io.FileReader
import kotlin.math.min

fun part1(filePath: String) {
    val file = File(filePath)
    val reader = FileReader(file)

    val sum: Int = reader.readLines().sumOf {
        val fstDigit = it.firstOrNull { ch -> ch.isDigit() }?.digitToInt()
        val sndDigit = it.lastOrNull { ch -> ch.isDigit() }?.digitToInt()
        fstDigit?.let { sndDigit?.let { fstDigit * 10 + sndDigit } } ?: 0
    }
    println(sum)
}

fun part2(filePath: String) {
    val file = File(filePath)
    val reader = FileReader(file)
    var sum = 0
    for (line in reader.readLines()) {
        val fstDigit = findDigit(line, line.indices)
        val sndDigit = findDigit(line, line.indices.reversed())
        sum += fstDigit * 10 + sndDigit
    }
    println(sum)
}

fun findDigit(line: String, indicesInOrder: IntProgression): Int {
    for (i in indicesInOrder) {
        val substring = line.substring(i, min(line.length, i + 10))
        val digit = when {
            substring.startsWith("one") -> 1
            substring.startsWith("two") -> 2
            substring.startsWith("three") -> 3
            substring.startsWith("four") -> 4
            substring.startsWith("five") -> 5
            substring.startsWith("six") -> 6
            substring.startsWith("seven") -> 7
            substring.startsWith("eight") -> 8
            substring.startsWith("nine") -> 9
            substring[0].isDigit() -> substring[0].digitToInt()
            else -> -1
        }
        if (digit > 0) {
            return digit
        }
    }
    return 0
}

fun main() {
    part1("src/main/resources/day1.txt")
    part2("src/main/resources/day1.txt")
}