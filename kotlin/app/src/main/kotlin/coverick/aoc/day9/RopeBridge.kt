package coverick.aoc.day9
import readResourceFile
val INPUT_FILE = "ropeBridge-input.txt"

fun part1(): Int {
    var hPos = Pair(0,0)
    var tPos = Pair(0,0)
    val tailSpots = hashSetOf(tPos)
    var instructions = readResourceFile(INPUT_FILE)
    val adjustTail = fun(){
        val xDelta = hPos.first - tPos.first
        val yDelta = hPos.second - tPos.second 
        if(xDelta > 1){
            if(yDelta >= 1){
                // need to move diagonal up right
                tPos = Pair(tPos.first + 1, tPos.second + 1)
            } else if(yDelta <= -1){
                // need to move diagonal down right 
                tPos = Pair(tPos.first + 1, tPos.second - 1)
            } else {
                // right 
                tPos = Pair(tPos.first + 1, tPos.second)
            }
        } else if(xDelta < -1){
            if(yDelta >= 1){
                // need to move diagonal up left
                tPos = Pair(tPos.first - 1, tPos.second + 1)
            } else if(yDelta <= -1){
                // need to move diagonal down left 
                tPos = Pair(tPos.first - 1, tPos.second - 1)
            } else {
                // left 
                tPos = Pair(tPos.first - 1, tPos.second)
            }
        } else if(yDelta > 1){
            if(xDelta >= 1){
                // need to move diagonal up right
                tPos = Pair(tPos.first + 1, tPos.second + 1)
            } else if(xDelta <= -1){
                // need to move diagonal up left 
                tPos = Pair(tPos.first - 1, tPos.second + 1)
            } else {
                // up 
                tPos = Pair(tPos.first, tPos.second + 1 )
            } 
        } else if(yDelta < -1){
            if(xDelta >= 1){
                // need to move diagonal down right
                tPos = Pair(tPos.first + 1, tPos.second - 1)
            } else if(xDelta <= -1){
                // need to move diagonal down left 
                tPos = Pair(tPos.first - 1, tPos.second - 1)
            } else {
                // down
                tPos = Pair(tPos.first, tPos.second - 1 )
            } 
        }
        tailSpots.add(tPos)
    }

    for(instruction in instructions){
        var direction = instruction.split(" ")[0]
        var magnitude = instruction.split(" ")[1].toInt()
        println("Head ${hPos} Tail ${tPos} Instruction ${instruction}")
        println("\tTail Positions Visited ${tailSpots}")
        when(direction){
            "R" -> {
                for(i in 1..magnitude){
                    hPos = Pair(hPos.first + 1, hPos.second)
                    adjustTail()
                }
            }
            "L" -> {
                for(i in 1..magnitude){
                    hPos = Pair(hPos.first - 1, hPos.second)
                    adjustTail()
                }
            }
            "U" -> {
                for(i in 1..magnitude){
                    hPos = Pair(hPos.first, hPos.second + 1)
                    adjustTail()
                }

            }
            "D" -> {
                for(i in 1..magnitude){
                    hPos = Pair(hPos.first, hPos.second - 1)
                    adjustTail()
                }
            }
        }
        tailSpots.add(tPos)
        println("Total tail spots visited: ${tailSpots.size}")
    }
    println("\tTail Positions Visited ${tailSpots}")
    return tailSpots.size
}

fun part2(): Int {
    return 0 
}

fun solution(){
    println("Rope Bridge Part 1 Solution ${part1()}")
    println("Rope Bridge Part 2 Solution ${part2()}")

}