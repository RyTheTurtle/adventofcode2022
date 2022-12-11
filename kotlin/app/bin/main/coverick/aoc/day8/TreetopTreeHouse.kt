package coverick.aoc.day8
import readResourceFile
val INPUT_FILE = "treetopTreeHouse-input.txt"


fun inputToGrid(input:List<String>):List<List<Int>> { 
    return input.map{ it.toCharArray().map{it - '0'} }.filter{it.size > 0}
}

fun part1(): Int {
    val grid = inputToGrid(readResourceFile(INPUT_FILE))
    val maxX = grid.get(0).size - 1
    val maxY = grid.size - 1

    val isVisibleCoordinate = fun(coordinate: Pair<Int,Int>):Boolean {
        // collect all coordinates we need
        val up = ArrayList<Int>()
        val down = ArrayList<Int>()
        val left = ArrayList<Int>()
        val right = ArrayList<Int>()
        var y = coordinate.first - 1
        while (y >= 0){
            up.add(grid.get(y).get(coordinate.second))
            y--
        }
        y = coordinate.first + 1
        while (y<grid.size){
            down.add(grid.get(y).get(coordinate.second))
            y++
        }
        var x = coordinate.second - 1
        while (x >= 0){
            left.add(grid.get(coordinate.first).get(x))
            x--
        }
        x = coordinate.second + 1
        while(x < grid.get(0).size){
            right.add(grid.get(coordinate.first).get(x))
            x++
        }
        val isEdge = up.isEmpty() || down.isEmpty() || left.isEmpty() || right.isEmpty()
        val MAX_HEIGHT = grid.get(coordinate.first).get(coordinate.second)
        return isEdge || up.all{it < MAX_HEIGHT} || down.all{it < MAX_HEIGHT} || left.all{it<MAX_HEIGHT} || right.all{it<MAX_HEIGHT} 
    }

    var visibleTreeCnt = 0
    for(row in 0..maxY){
        for(col in 0..maxX) {
            if(isVisibleCoordinate(Pair(col,row))){
                visibleTreeCnt++
            }
        }   
    }
    return visibleTreeCnt
}

fun part2(): Int {
    return 0
}

fun solution(){
    println("Treetop Tree House Part 1 Solution: ${part1()}")
    println("Treetop Tree House Part 2 Solution: ${part2()}")
}