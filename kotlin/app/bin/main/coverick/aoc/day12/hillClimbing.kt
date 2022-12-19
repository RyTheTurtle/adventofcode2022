package coverick.aoc.day12 
import java.util.PriorityQueue 
import readResourceFile

val INPUT_FILE = "hillClimbing-input.txt"
typealias Coordinate = Pair<Int,Int>

data class Path(var current: Coordinate, val nodes:ArrayList<Coordinate>){}

class Hill(val heightMap:List<CharArray>, val start: Char, val end:Char){
    var startPos: Coordinate = Coordinate(0,0)
    var endPos: Coordinate   = Coordinate(0,0)
    val unvisited = HashSet<Coordinate>()

    // determine start and end coordinates on grid
    init {
        for(i in 0..heightMap.size-1){
            for(j in 0..heightMap.get(i).size - 1){
                if(heightMap.get(i).get(j) == start){
                    startPos = Coordinate(i,j)
                } else if (heightMap.get(i).get(j) == end){
                    endPos = Coordinate(i,j)
                }
                unvisited.add(Coordinate(i,j))
            }
        }
        println("Start height: ${getHeight(startPos)} endHeight: ${getHeight(endPos)}")
    }

    // replace heights of start and end with 'a' and 'z' respectively
    init {
        heightMap.get(startPos.first).set(startPos.second, 'a') 
        heightMap.get(endPos.first).set(endPos.second, 'z')
    }

    fun getHeight(c:Coordinate) : Char {
        return heightMap.get(c.first).get(c.second) 
    }

    fun isValidCoordinate(c:Coordinate): Boolean {
        return c.first >= 0 && c.first < heightMap.size 
        && c.second >= 0 && c.second < heightMap.get(0).size
    }

    fun isValidMove(from:Coordinate, to:Coordinate): Boolean {
        val fromHeight = getHeight(from) 
        val toHeight   = getHeight(to) 
        println("${from} ${fromHeight} , ${to} ${toHeight} (${toHeight - fromHeight})")
        return toHeight - fromHeight <= 1 && toHeight - fromHeight >= -1
    }

    // return only unvisited adjacent nodes not including diagonals
    fun getNextMoves(c:Coordinate) : List<Coordinate>{
        return hashSetOf(
            Coordinate(c.first + 1, c.second),
            Coordinate(c.first - 1, c.second),
            Coordinate(c.first, c.second - 1),
            Coordinate(c.first, c.second + 1)
        ).filter{ // unvisited.contains(it) && 
                  isValidCoordinate(it) &&
                  isValidMove(c,it)}
    }

    fun getShortestPathToEnd(): Int {
        val paths =  PriorityQueue<Path>(compareBy({it.nodes.size}))
        paths.add(Path(startPos, arrayListOf(startPos)))
        val LIMITER = 10000000L // in case logic is screwed, avoids infinite while loop
        for(i in 0..LIMITER){
            if(paths.size == 0){
                println("Ran out of paths after ${i} iterations")
                break
            }
            val currentPath = paths.poll()
            println("Current path length: ${currentPath.nodes.size}")
            if(currentPath.current == endPos){
                // don't count being on the end as a step, so -1 
                println("${currentPath}")
                return currentPath.nodes.size - 1
            }
            val nextMoves = getNextMoves(currentPath.current).filter{!currentPath.nodes.contains(it)} 
            if(nextMoves.size == 0){
                println("Reached dead end  at ${currentPath.current} for path ${currentPath.nodes}")
            }
            for(move in nextMoves){
                val newPath = Path(move, ArrayList<Coordinate>())
                newPath.nodes.addAll(currentPath.nodes)
                newPath.nodes.add(move)
                paths.add(newPath)
                unvisited.remove(move)
                // heightMap.get(currentPath.current.first).set(currentPath.current.second, '*')
            }
        }
        println("Hit rate limiter of ${LIMITER} without finding a valid path")
        for(r in heightMap){
            println("${r.joinToString("")}")
        }
        return -1
    }
}


fun part1():Int{
    val heightMap = readResourceFile(INPUT_FILE).map{it.toCharArray()}
    val hill = Hill(heightMap, 'S', 'E')
    return hill.getShortestPathToEnd()
}

fun part2(): Int{
    return 0
}

fun solution(){
    println("Hill Climbing Algorithm Part 1: ${part1()}")
    println("Hill Climbing Algorithm Part 2: ${part2()}")
}