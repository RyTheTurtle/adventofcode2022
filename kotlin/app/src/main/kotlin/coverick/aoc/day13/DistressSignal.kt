package coverick.aoc.day13
import readResourceFile
import splitOn 
var INPUT_FILE = "distressSignal-input.txt"

enum class DatumType { NUMBER, LIST}
data class Datum(val type: DatumType, val value:List<Int>)
data class Packet(val data: ArrayList<Datum>)

class DatumComparator {

    companion object : Comparator<Datum> { 
        override fun compare(a:Datum, b:Datum): Int {
            var result = 0
            // since datums store numbers as a list of a single value, this 
            // logic can apply for both list-list and number-list comparisons and number-number comparisons
            var idx = 0
            while(idx < a.value.size && idx < b.value.size){
                result = a.value.get(idx).compareTo(b.value.get(0))
                idx++
            }
            // if list were unequal size, smaller list should have been on left side
            if(idx < a.value.size){
                result = -1
            }else if(idx < b.value.size){
                result = 1
            }
            return result
        }
    }
}
fun parsePacket(packetStr: String): Packet{ 
    var idx = 0
    val result = Packet(ArrayList<Datum>())
    // define a few helpers for parsing 
    fun isEndOfPacket(): Boolean {
        return idx >= packetStr.length
    }

    fun eat(c:Char){
        if(packetStr.get(idx) == c){
            idx++
        } else {
            throw RuntimeException("Expected ${c} but found ${packetStr.get(idx)}")
        }
    }

    fun peek():Char {
        return packetStr.get(idx)
    }

    fun advance(){
        idx++
    }

    fun parseIntDatum(): Datum{
        val buf:StringBuilder = StringBuilder()
        buf.append(packetStr.get(idx))
        idx++
        while(!isEndOfPacket() && packetStr.get(idx).isDigit()){
            buf.append(packetStr.get(idx))
            advance()
        }
        return Datum(DatumType.NUMBER, arrayListOf(buf.toString().toInt()))
    }

    fun parseListDatum():Datum{
        eat('[')
        val intList = ArrayList<Datum>()
        while(!isEndOfPacket() && packetStr.get(idx) != ']'){
            intList.add(parseIntDatum())
            if(peek() == ','){
                eat(',')
            } else {
                break
            }
        }
        return Datum(DatumType.LIST, intList.map{it.value.get(0)})
    }

    eat('[')
    
    while(!isEndOfPacket()){
        when(packetStr.get(idx)){
            '[' ->  {
                result.data.add(parseListDatum())
                eat(']')
            }
            ']' ->  return result
            ',' ->  eat(',')
            else -> {
                result.data.add(parseIntDatum())
                eat(',')
            }
        }
    }
    return result
}

fun part1(): Int {
    /**
     * Approach 
     *  split input on empty lines
     * 
     *  need to build a packet tokenizer and parser 
     *  
     *  check how many packets are ordered properly 
     * 
     *  when comparing left and right numbers, left should be smaller 
     * 
     *  when comparing two lists, compare their indices for the left being smaller
     * than the right. if no determination of improper ordering is found, if left runs out 
     * of items first, it's in the right order, if right 
     * 
     *  when comparing list and int, convert int to list of one value 
     */
    val packetGroups = readResourceFile(INPUT_FILE).splitOn { it.isEmpty()}
    return 0 
}

fun part2(): Int{
    return 0 
}

fun solution() {
    println("Distress Signal Part 1 solution: ${part1()}")
    println("Distress Signal Part 2 solution: ${part2()}")

}