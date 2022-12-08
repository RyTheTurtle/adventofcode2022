package coverick.aoc.day5

import readResourceFile
import splitOn

val INPUT_FILE = "supplyStacks-input.txt"

fun parseConfigLine(s:String): List<String>{
    var idx = 0
    val result = ArrayList<String>()
    val eatCrate = fun() { 
        if(idx < s.length && s[idx] == '['){
            idx++
            result.add(s[idx].toString())
            idx+= 2 // skip closing ]
        } else {
            idx += 3 // nothing in this slot, advance past crate width
            result.add("")
        }
    }
    val eatSpace = fun(){
        if(idx < s.length && s[idx].isWhitespace()){
            idx++
        }
    }
    while(idx < s.length){
        eatCrate()
        eatSpace()
    }
    return result 
}

/*
string format is expected to be 
"move [crates] from [start] to [end]"
returns a triple of [crates to move, source stack, destination stack] 
*/
fun parseMoveInstruction(s:String): Triple<Int,Int,Int> {
    val parts = s.split(" ")
    return Triple(parts[1].toInt() ,
                  parts[3].toInt() - 1,
                  parts[5].toInt() - 1)
}

fun applyMoveInstruction(stacks:ArrayList<ArrayDeque<String>>, 
                         instruction: Triple<Int,Int,Int>) {
    
    for(i in 1..instruction.first){
        stacks.get(instruction.third).addLast(
            stacks.get(instruction.second).removeLast()
        )
    }
}


fun applyMultiCrateMoveInstruction(stacks:ArrayList<ArrayDeque<String>>, 
                         instruction: Triple<Int,Int,Int>) {
        
    val movedCrates = stacks.get(instruction.second)
    .takeLast(instruction.first)
    
    stacks.get(instruction.third)
    .addAll(movedCrates) 

    for(i in 0..instruction.first-1){
        stacks.get(instruction.second).removeLast()
    }
}

fun getStartingCrates(input: List<String>): ArrayList<ArrayDeque<String>> {
    val stacks = ArrayList<ArrayDeque<String>>()
    val startingConfig = input.map{parseConfigLine(it)}
    for(cratesRow in startingConfig){
        while(stacks.size < cratesRow.size){
            stacks.add(ArrayDeque<String>())
        }
        for((idx,crate) in cratesRow.withIndex()){
            if(crate.isNotEmpty()){
                stacks.get(idx).addFirst(crate)
            }
        }
    }
    return stacks
}

fun part1(): String {
    val inputs = readResourceFile(INPUT_FILE).splitOn{it.isEmpty()}
    val stacks = getStartingCrates(inputs[0]) 

    inputs[1].map{parseMoveInstruction(it)}
    .forEach{applyMoveInstruction(stacks,it)}

    return stacks.map{it.last()}.joinToString(separator="")
}

fun part2(): String {
    val inputs = readResourceFile(INPUT_FILE).splitOn{it.isEmpty()}
    val stacks = getStartingCrates(inputs[0]) 
    
    inputs[1].map{parseMoveInstruction(it)}
    .forEach{applyMultiCrateMoveInstruction(stacks,it) }

    return stacks.map{it.last()}.joinToString(separator="")
}

fun solution(){
    println("Supply Stacks part 1 solution: ${part1()}")
    println("Supply Stacks part 2 solution: ${part2()}")

}