package coverick.aoc.day2;
import readResourceFile

// https://adventofcode.com/2022/day/2
val INPUT_FILE = "rockPaperScissors-input.txt"

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

// Wire up config tables for use in scoring and parsing 
val SHAPE_LETTER_MAPPINGS = mapOf(
    "A" to Shape.ROCK, 
    "X" to Shape.ROCK,
    "B" to Shape.PAPER,
    "Y" to Shape.PAPER,
    "C" to Shape.SCISSOR,
    "Z" to Shape.SCISSOR
)

// configure scoring rules for 2 player rock,paper,scissors
// as a multidimensional map . First choice is opponent, second 
// choice is my choice 
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


fun part1(): Int{
    // return total score for strategy guide
    // use a lookup table for configuring scoring 
    val rounds = readResourceFile(INPUT_FILE)
    var totalScore = 0 
    rounds.forEach({
        val inputs = it.split(" ")
        val myChoice = SHAPE_LETTER_MAPPINGS.get(inputs[1])
        val opponentChoice = SHAPE_LETTER_MAPPINGS.get(inputs[0]); 
        val roundScore: Int = TWO_PLAYER_SCORE_MAPPING.get(opponentChoice)?.get(myChoice)?.score?:0
        val choiceScore: Int = myChoice?.score ?:0
        totalScore += roundScore + choiceScore 
    })
    return totalScore
}

fun solution(){
    println("Rock,Paper,Scissors Part 1 Solution: ${part1()}")
}