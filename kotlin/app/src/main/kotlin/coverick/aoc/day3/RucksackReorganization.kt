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

fun getDuplicatedItem(elfGroup: Triple<String,String,String>): Char {
    return elfGroup
            .first
            .toCharArray()
            .intersect(elfGroup.second.toList())
            .intersect(elfGroup.third.toList())
            .first()
}

fun getElfGroups(rucks:List<String>)
: List<Triple<String,String,String>> {
    val elfGroups =  ArrayList<Triple<String,String,String>>()
    for(ruck in 0..rucks.size-1 step 3){
        elfGroups.add(Triple(rucks[ruck],rucks[ruck+1],rucks[ruck+2]))
    } 
    return elfGroups    
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
    // craft lookup table for priorities of items
    val priority = HashMap<Char,Int>()
    for(c in 'a'..'z'){
        priority.put(c, c.code - 97 + 1)
    }
    for(c in 'A'..'Z'){
        priority.put(c, c.code - 65 + 27)
    }

    return getElfGroups(readResourceFile(INPUT_FILE))
            .map { getDuplicatedItem(it) }
            .map { priority.getOrElse(it) {0}}
            .sum()
}

fun solution(){
    println("Rucksack Reorganization Part 1 Solution: ${part1()}")
    println("Rucksack Reorganization Part 1 Solution: ${part2()}")

}