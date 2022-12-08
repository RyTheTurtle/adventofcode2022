/**
common util functions for things like reading input files
 */
import java.io.File;

fun readResourceFile(fileName: String): List<String> 
  = object {}.javaClass.getResourceAsStream(fileName).bufferedReader().readLines()

/**
partitions a list of strings to segements that
are separated by a string that matches some predicate. 
Excludes the separator lines from the output. 

Example: 

val input = ["The","----","big","----","cat","ran"]
val sep = (s) -> s.equals("----")
val result = input.splitOn(sep)

result is [["The"],["big"],["cat","ran"]]
 */
fun List<String>.splitOn(f: (String) -> Boolean): List<List<String>> { 
    val result = ArrayList<List<String>>()
    var current = ArrayList<String>()
    for(s in this){
        if(f(s)){
            if(current.size > 0){
                result.add(current)
            }
            current = ArrayList<String>()
        } else {
            current.add(s)
        }
    }
    if(current.size > 0){
        result.add(current)
    }
    return result
}