package coverick.aoc.day10
import readResourceFile
val INPUT_FILE = "cathodeRayTube-input.txt"

class CRT(x:Int) {
    var clock: Int 
    var pixels: ArrayList<ArrayList<Char>>
    val WIDTH = x
    init {
        clock = 1
        pixels = ArrayList<ArrayList<Char>>() 
        pixels.add(ArrayList<Char>())
    }

    fun draw(sprite: Int) {
        val row = clock / WIDTH 
        if(row > pixels.size -1 ){
            pixels.add(ArrayList<Char>())
        }
        val col = clock % WIDTH
        val isLit = col >= sprite-1 && col <= sprite+1
        if(isLit){
            pixels.get(row).add('#')
        } else {
            pixels.get(row).add('.')
        }
        clock++
    }
}

class CPU(crt: CRT) {
    var registerValue = 1
    var clock = 1
    val importantCycles = setOf(20,60,100,140,180,220)
    val crt = crt 
    
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
                crt.draw(registerValue)
            }
            "addx" -> {
                val value = cmd.split(" ")[1].toInt()
                clock += 1
                if(clock in importantCycles){ 
                    result = registerValue * clock
                }
                crt.draw(registerValue)
                registerValue += value 
                clock += 1
                if(clock in importantCycles){ 
                    result = registerValue * clock
                }
                crt.draw(registerValue)
            }
        }
        return result
    }
}

fun part1(): Int {
    val instructions = readResourceFile(INPUT_FILE)
    val crt = CRT(40)
    val cpu = CPU(crt)
    var sum = 0
    for(cmd in instructions){
       val res = cpu.execute(cmd)
       if(res != null){
          sum += res
       }
    }
    return sum
}

fun part2(): CRT {
    val instructions = readResourceFile(INPUT_FILE)
    val crt = CRT(40)
    val cpu = CPU(crt)
    instructions.forEach{cpu.execute(it)}
    return crt
}

fun solution(){
    println("Cathode-Ray Tube Solution Part 1 Solution ${part1()}")
    println("Cathode-Ray Tube Solution Part 2 Solution")
    val part2 = part2()
    for(row in part2.pixels){
        println("\t${row.joinToString("")}")
    }

}