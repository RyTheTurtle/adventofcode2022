/**
common util functions for things like reading input files
 */
import java.io.File;

fun readResourceFile(fileName: String): List<String> 
  = object {}.javaClass.getResourceAsStream(fileName).bufferedReader().readLines()