package coverick.aoc.day9
import readResourceFile
val INPUT_FILE = "ropeBridge-input.txt"

/**
 * given a head and tail coordinate, move the tail appropriately 
 * such that the tail is adjacent to the head. assumption: the head is not 
 * more than one move away from the tail. Otherwise we'd just repeat this function 
 * until we got to a state where the tail doesn't need to move. 
 * 
 * returns: the pair of ints representing the coordinate for the tail after adjustment
 */
val adjustTail = fun(head: Pair<Int,Int>, tail: Pair<Int,Int>):Pair<Int,Int>{
    val xDelta = head.first - tail.first
    val yDelta = head.second - tail.second 
    var tPos   = tail 
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
    return tPos
}

val getTail = fun(snake: ArrayList<Pair<Int,Int>>):Pair<Int,Int>{
    return snake.get(snake.size - 1)
}

val getSnake = fun(size:Int): ArrayList<Pair<Int,Int>> { 
    val snake = ArrayList<Pair<Int,Int>>()
    for(i in 1..size){
        snake.add(Pair(0,0))
    }
    return snake 
}

/**
 * process a set of move instructions and track the coordinates where the snake tail has landed
 * return the set of unique coordinates the snake tail has touched. 
 */
val trackSnakeTail = fun(snake: ArrayList<Pair<Int,Int>>, instructions: List<String>): Set<Pair<Int,Int>> {
    val tailSpots = hashSetOf(getTail(snake))
    for(instruction in instructions){
        var direction = instruction.split(" ")[0]
        var magnitude = instruction.split(" ")[1].toInt() 
        var hPos = snake.get(0)
        
        for(i in 1..magnitude){
            when(direction){
                "R" -> snake.set(0, Pair(hPos.first + 1, hPos.second))
                "L" -> snake.set(0, Pair(hPos.first - 1, hPos.second))
                "U" -> snake.set(0, Pair(hPos.first, hPos.second + 1))
                "D" -> snake.set(0, Pair(hPos.first, hPos.second - 1))
            }
            hPos = snake.get(0)
            for(j in 1..snake.size-1){
                // println("${snake}")
                snake.set(j, adjustTail(snake.get(j-1),snake.get(j)))
            }
            tailSpots.add(getTail(snake))
        }
    } 
    return tailSpots
}

fun part1(): Int {
    var instructions = readResourceFile(INPUT_FILE)
    val SNAKE_LENGTH = 2
    val snake = getSnake(SNAKE_LENGTH)
    return trackSnakeTail(snake,instructions).size
}

fun part2(): Int {
    // same as part 1, just with a longer snake
    var instructions = readResourceFile(INPUT_FILE)
    val SNAKE_LENGTH = 10
    val snake = getSnake(SNAKE_LENGTH)
    return trackSnakeTail(snake,instructions).size 
}


fun solution(){
    println("Rope Bridge Part 1 Solution ${part1()}")
    println("Rope Bridge Part 2 Solution ${part2()}")
}