package coverick.aoc.day6
import readResourceFile

val INPUT_FILE = "tuningTrouble-input.txt"

fun part1(): Int {
    val inputBuffer = readResourceFile(INPUT_FILE)[0]
    val startOfPacketMarkerSize = 4
    val packetMarkerBuffer = ArrayDeque<Char>(startOfPacketMarkerSize)
    var currentIndex = 0
    // fill buffer with minimum starting chars
    for(i in 1..startOfPacketMarkerSize){
        packetMarkerBuffer.add(inputBuffer.get(currentIndex))
        currentIndex++
    }
    while(currentIndex < inputBuffer.length){
        if(packetMarkerBuffer.size == packetMarkerBuffer.distinct().size){
            break
        }
        currentIndex ++
        packetMarkerBuffer.removeFirst()
        packetMarkerBuffer.addLast(inputBuffer.get(currentIndex))
    }
    return currentIndex+1;
}

fun part2():Int {
    return 0
}

fun solution(){
    println("Tuning Trouble part 1 solution: ${part1()}")
    println("Tuning Trouble part 1 solution: ${part2()}")

}