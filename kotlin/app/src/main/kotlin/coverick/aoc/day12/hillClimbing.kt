package coverick.aoc.day12 
import java.util.PriorityQueue 
import java.util.HashSet
import readResourceFile

val INPUT_FILE = "hillClimbing-input.txt"
typealias Coordinate = Pair<Int,Int>

data class Path(var current: Coordinate, val nodes:HashSet<Coordinate>){}

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
        return toHeight - fromHeight <= 1 
    }

    // return only unvisited adjacent nodes not including diagonals
    fun getNextMoves(c:Coordinate) : List<Coordinate>{
        return hashSetOf(
            Coordinate(c.first + 1, c.second),
            Coordinate(c.first - 1, c.second),
            Coordinate(c.first, c.second - 1),
            Coordinate(c.first, c.second + 1)
        ).filter{ isValidCoordinate(it) &&
                  isValidMove(c,it)}
    }

    fun getShortestPathToEnd(): Int {
        // return dfs(startPos, 0, HashSet<Coordinate>()) // 683
        return bfs(startPos)
        
    }

    fun bfs(curNode: Coordinate): Int {
        // keep track of visited nodes
        val visited = HashSet<Coordinate>()
        val distances = HashMap<Coordinate, Int>()
        // order by current distance from origin point 
        val q = PriorityQueue<Coordinate>(compareBy{distances.getOrDefault(it, Int.MAX_VALUE)})
        distances.put(startPos, 0)
        visited.add(startPos)
        var result = Int.MAX_VALUE
        q.add(startPos)
        while(q.size > 0){
            val current = q.poll()
            val neighbors = getNextMoves(current)
            for(neighbor in neighbors){
                if(neighbor == endPos){
                    println("Reached end position")
                    if(distances.getOrDefault(current, Int.MAX_VALUE) < result){
                        result = distances.getOrDefault(current, Int.MAX_VALUE) + 1
                    }
                } else if(!visited.contains(neighbor)){
                    distances.put(neighbor, distances.getOrDefault(current, 0)+1)
                    visited.add(neighbor)
                    q.add(neighbor)
                }
            }
        }
        return result
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