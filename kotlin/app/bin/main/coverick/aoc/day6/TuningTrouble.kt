package coverick.aoc.day6
import readResourceFile

val INPUT_FILE = "tuningTrouble-input.txt"

fun findMarker(input:String, n:Int): Int {
    val buf = ArrayDeque<Char>(n)
    var currentIdx = 0
    for(i in 1..n){
        buf.add(input.get(currentIdx))
        currentIdx++
    }

    while(currentIdx < input.length){
        if(buf.size == buf.distinct().size){
            break
        }
        currentIdx ++
        buf.removeFirst()
        buf.addLast(input.get(currentIdx))
    }
    return currentIdx+1;
}

fun part1(): Int {
    return findMarker(readResourceFile(INPUT_FILE)[0], 4)
}

fun part2():Int {
    return findMarker(readResourceFile(INPUT_FILE)[0], 14)
}

fun solution(){
    println("Tuning Trouble part 1 solution: ${part1()}")
    println("Tuning Trouble part 1 solution: ${part2()}")

}