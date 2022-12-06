package coverick.aoc.day4
import readResourceFile

//https://adventofcode.com/2022/day/4
val INPUT_FILE = "campCleanup-input.txt"


fun isOverlapped(it:List<String>):Boolean {
    val leftRanges  = it[0].split("-").map { Integer.valueOf(it)}
    val rightRanges = it[1].split("-").map { Integer.valueOf(it)}
    val leftContainsRight = leftRanges[0] <= rightRanges[0] && leftRanges[1] >= rightRanges[1]
    val rightContainsLeft = rightRanges[0] <= leftRanges[0] && rightRanges[1] >= leftRanges[1]
    return leftContainsRight || rightContainsLeft
}

fun part1(): Int {
    return readResourceFile(INPUT_FILE)
    .map { it.split(",") }
    .filter{ isOverlapped(it) }
    .count()
}


fun part2(): Int {
    return 0 
}

fun solution(){
    println("Camp Cleanup Part 1 Solution: ${part1()}")
    println("Camp Cleanup Part 2 Solution: ${part2()}")

}