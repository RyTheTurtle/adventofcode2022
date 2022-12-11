package coverick.aoc.day10
import readResourceFile
val INPUT_FILE = "cathodeRayTube-input.txt"


class CPU {
    var registerValue = 1
    var clock = 1
    val importantCycles = setOf(20,60,100,140,180,220)
    
    // returns non null if during this cmd we have an important cycle needed
    fun execute(cmd: String): Int? {
        val instruction = cmd.split(" ")[0]
        var result: Int? = null 
        when(instruction){
            "noop" -> {
                clock += 1
                if(clock in importantCycles){ 
                    result = registerValue * clock
                }
            }
            "addx" -> {
                val value = cmd.split(" ")[1].toInt()
                clock += 1
                if(clock in importantCycles){ 
                    result = registerValue * clock
                }
                registerValue += value 
                clock += 1
                if(clock in importantCycles){ 
                    result = registerValue * clock
                }
            }
        }
        return result
    }
}

fun part1(): Int {
    val instructions = readResourceFile(INPUT_FILE)
    val cpu = CPU()
    var sum = 0
    for(cmd in instructions){
       val res = cpu.execute(cmd)
       if(res != null){
          sum += res
       }
    }
    return sum
}

fun part2(): Int {
    return 0 
}

fun solution(){
    println("Cathode-Ray Tube Solution Part 1 Solution ${part1()}")
    println("Cathode-Ray Tube Solution Part 2 Solution ${part2()}")

}