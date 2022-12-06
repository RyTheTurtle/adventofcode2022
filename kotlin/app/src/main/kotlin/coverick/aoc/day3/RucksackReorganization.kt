package coverick.aoc.day3
import readResourceFile

//https://adventofcode.com/2022/day/3
val INPUT_FILE = "rucksackReorganization-input.txt"

fun getRucksackCompartments(contents:String): Pair<String,String>{
    val split = contents.length / 2
    return Pair(contents.substring(0,split),
                 contents.substring(split))
}

fun getDuplicatedItem(ruck:Pair<String,String>): Char {
    return ruck
            .first
            .toCharArray()
            .intersect(ruck.second.toList())
            .first()
}

fun part1(): Int {
    // craft lookup table for priorities of items
    val priority = HashMap<Char,Int>()
    for(c in 'a'..'z'){
        priority.put(c, c.code - 97 + 1)
    }
    for(c in 'A'..'Z'){
        priority.put(c, c.code - 65 + 27)
    }

    return readResourceFile(INPUT_FILE)
        .map { getRucksackCompartments(it) }
        .map{getDuplicatedItem(it)}
        .map{priority.getOrElse(it) { 0 }} 
        .sum()
}

fun part2(): Int {
    return 0 // TODO implement 
}

fun solution(){
    println("Rucksack Reorganization Part 1 Solution: ${part1()}")
    println("Rucksack Reorganization Part 1 Solution: ${part2()}")

}