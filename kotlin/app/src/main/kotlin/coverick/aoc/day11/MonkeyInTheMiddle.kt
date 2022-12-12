package coverick.aoc.day11 
import readResourceFile
val INPUT_FILE = "monkeyInTheMiddle-input.txt"

class Monkey(startingItems: ArrayList<Long>, 
            op: (Long) -> Long, 
            test: (Long)-> Int) {
    val items = startingItems
    val op = op 
    val test = test 
    var inspections = 0

    fun inspect(item:Long) :Long{
        inspections++
        return op(item)
    }
}

fun getProgramInput(): ArrayList<Monkey> {
    // avoid parsing because that's not the objective of the program, just hardcoding inputs for this one 
    val monkeys = ArrayList<Monkey>() 
    monkeys.add(
        Monkey(
            arrayListOf(84, 72, 58, 51L), 
            {l -> l * 3}, 
            {l ->  if (l % 13 == 0L ) 1 else 7 })
    )
    monkeys.add(
        Monkey(
            arrayListOf(88L, 58, 58), 
            {l -> l + 8}, 
            {l ->  if (l % 2 == 0L ) 7 else 5 })
    )
    monkeys.add(
        Monkey(
            arrayListOf(93L, 82, 71, 77, 83, 53, 71, 89), 
            {l -> l * l}, 
            {l ->  if (l % 7 == 0L ) 3 else 4})
    )
    monkeys.add(
        Monkey(
            arrayListOf(81L, 68, 65, 81, 73, 77, 96), 
            {l -> l + 2}, 
            {l ->  if (l % 17 == 0L ) 4 else 6 })
    )
    monkeys.add(
        Monkey(
            arrayListOf(75L, 80, 50, 73, 88), 
            {l -> l + 3}, 
            {l ->  if (l % 5 == 0L ) 6 else 0})
    )
    monkeys.add(
        Monkey(
            arrayListOf(59L, 72, 99, 87, 91, 81), 
            {l -> l * 17}, 
            {l ->  if (l % 11 == 0L ) 2 else 3})
    )
    monkeys.add(
        Monkey(
            arrayListOf(86L , 69), 
            {l -> l + 6}, 
            {l ->  if (l % 3 == 0L ) 1 else 0 })
    )
    monkeys.add(
        Monkey(
            arrayListOf(91L), 
            {l -> l + 1}, 
            {l ->  if (l % 19 == 0L ) 2 else 5 })
    )
    return monkeys
}

fun part1(): Int {
    var monkeys = getProgramInput()
    val ROUNDS = 20 
    val relief : (Long) -> Long = {l -> l / 3}

    for(i in 1..ROUNDS){
        for(monkey in monkeys){
            while(monkey.items.size > 0){
                var item =  monkey.items.removeAt(0)
                // monkey inspects, then we get relief
                item = relief(monkey.inspect(item))

                val monkeyToThrowTo = monkey.test(item) 
                monkeys.get(monkeyToThrowTo).items.add(item)
            }
        }
    }

    val monkeysSortedByInspections = monkeys.map { it.inspections}.sortedDescending()
    return monkeysSortedByInspections.get(0) * monkeysSortedByInspections.get(1)
}

fun part2(): Int {
    return 0
}

fun solution(){
    println("Monkey In The Middle part 1 solution: ${part1()}")
    println("Monkey In The Middle part 2 solution: ${part2()}")

}