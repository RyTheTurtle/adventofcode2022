package coverick.aoc.day2;
import readResourceFile

// https://adventofcode.com/2022/day/2
val INPUT_FILE = "rockPaperScissors-input.txt"

// enumerate shapes and outcomes for rock,paper,scissors 
// according to text rules.
enum class Shape(val score: Int) {
    ROCK(1),
    PAPER(2), 
    SCISSOR(3)
}

enum class Outcome(val score: Int){
    WIN(6),
    LOSE(0),
    DRAW(3)
}

// map input letters to shapes and outcomes for the game
// based on rules (outcome letter mappings are from part 2)
val SHAPE_LETTER_MAPPINGS = mapOf(
    "A" to Shape.ROCK, 
    "X" to Shape.ROCK,
    "B" to Shape.PAPER,
    "Y" to Shape.PAPER,
    "C" to Shape.SCISSOR,
    "Z" to Shape.SCISSOR
)


val OUTCOME_LETTER_MAPPING = mapOf(
    "X" to Outcome.LOSE,
    "Y" to Outcome.DRAW,
    "Z" to Outcome.WIN
)

// configure scoring rules for 2 player rock,paper,scissors
// as a multidimensional map opponent shape -> my shape -> outcome
val TWO_PLAYER_SCORE_MAPPING = mapOf(
    Shape.ROCK to mapOf(
        Shape.ROCK to Outcome.DRAW,
        Shape.PAPER to Outcome.WIN,
        Shape.SCISSOR to Outcome.LOSE
    ),

    Shape.PAPER to mapOf(
        Shape.ROCK to Outcome.LOSE,
        Shape.PAPER to Outcome.DRAW,
        Shape.SCISSOR to Outcome.WIN
    ),

    Shape.SCISSOR to mapOf(
        Shape.ROCK to Outcome.WIN,
        Shape.PAPER to Outcome.LOSE,
        Shape.SCISSOR to Outcome.DRAW
    )
)


/* for part 2, configure mapping of 
opponent shape -> desired outcome -> my shape to get that outcome
*/
val OPPONENT_OUTCOME_CONFIG = mapOf(
    Shape.ROCK to mapOf(
        Outcome.DRAW to Shape.ROCK,
        Outcome.WIN to Shape.PAPER,
        Outcome.LOSE to Shape.SCISSOR
    ),

    Shape.PAPER to mapOf(
        Outcome.DRAW to Shape.PAPER,
        Outcome.WIN to Shape.SCISSOR,
        Outcome.LOSE to Shape.ROCK
    ),

    Shape.SCISSOR to mapOf(
        Outcome.DRAW to Shape.SCISSOR,
        Outcome.WIN to Shape.ROCK,
        Outcome.LOSE to Shape.PAPER 
    )
)

fun scoreRound(opponent: Shape?, me:Shape?) : Int {
    val outcomeScore: Int = TWO_PLAYER_SCORE_MAPPING.get(opponent)?.get(me)?.score?:0
    val choiceScore: Int = me?.score?:0
    return outcomeScore + choiceScore
}

fun part1(): Int{
    val rounds = readResourceFile(INPUT_FILE)
    var totalScore = 0 
    rounds.forEach({
        val inputs = it.split(" ")
        val myChoice = SHAPE_LETTER_MAPPINGS.get(inputs[1])
        val opponentChoice = SHAPE_LETTER_MAPPINGS.get(inputs[0])
        totalScore += scoreRound(opponentChoice, myChoice)
    })
    return totalScore
}


fun part2(): Int{
    val rounds = readResourceFile(INPUT_FILE)
    var totalScore = 0 
    rounds.forEach({
        val inputs = it.split(" ")
        val opponentChoice = SHAPE_LETTER_MAPPINGS.get(inputs[0])
        val desiredOutcome = OUTCOME_LETTER_MAPPING.get(inputs[1])
        val myChoice = OPPONENT_OUTCOME_CONFIG.get(opponentChoice)?.get(desiredOutcome)
        totalScore += scoreRound(opponentChoice, myChoice)
    })
    return totalScore
}

fun solution(){
    println("Rock,Paper,Scissors Part 1 Solution: ${part1()}")
    println("Rock,Paper,Scissors Part 2 Solution: ${part2()}")
}