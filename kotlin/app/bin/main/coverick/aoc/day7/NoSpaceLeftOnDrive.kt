package coverick.aoc.day7
import readResourceFile
val INPUT_FILE = "noSpaceLeftOnDevice-input.txt"

class Dir(val path:String, val parent: Dir?){
    val files = ArrayList<Long>()
    val subDirs = ArrayList<Dir>()

    fun nav(nextPath: String) :Dir {
        return when(nextPath){
            ".."-> when(this.parent){
                null -> this 
                else -> this.parent 
            }

            "/" -> when(this.parent){
                        null -> this 
                        else -> this.parent.nav(nextPath)
            }

            else -> {
                var nextDir = this.subDirs.filter { it.path.startsWith(nextPath) }.getOrNull(0)
                if(nextDir == null){
                    nextDir = Dir(nextPath, this)
                    this.subDirs.add(nextDir)
                }
                nextDir
            }
        }
    }

    fun getSize():Long {
        return files.sum() + subDirs.map{it.getSize()}.sum()
    }

    override fun toString(): String {
        if(parent != null){
            return "${parent.toString()}${path}/"
        }
        return  "${path}"
    }
}

fun part1(): Long {
    val isDir : (String) -> Boolean = {it -> it.startsWith("dir")}
    val isCommand : (String)-> Boolean = {it -> it.startsWith("$")}
    val isFile : (String) -> Boolean = {it -> try{
        it.split(" ")[0].toInt()
        true
    }catch(e: Exception){
        false
    }}
    val getFileSize : (String) -> Long = { it.split(" ")[0].toLong()}
    val isList : (String) -> Boolean = {it -> it.split(" ")[1].equals("ls")}
    val isNav  : (String) -> Boolean = {it -> it.split(" ")[1].equals("cd")}
    
    var rootNode = Dir("/", null) 
    val lines = readResourceFile(INPUT_FILE)
    var i = 0
    while(i< lines.size) {
        var line = lines[i]
        if(isCommand(line)){
            if(isNav(line)){
                rootNode = rootNode.nav(line.split(" ")[2])
                i++
            } else if(isList(line)){
                i++
                line = lines[i]
                while(!isCommand(line)){
                    if(isFile(line)){
                        rootNode.files.add(getFileSize(line))
                    }
                    i++
                    if(i < lines.size){
                        line = lines[i]
                    } else {
                        break
                    }
                }
            }
        } else {
            i++
        }
    }
    
    rootNode = rootNode.nav("/") 
    val currentNodes = ArrayList<Dir>()
    currentNodes.add(rootNode)
    val TARGET_SIZE = 100000
    var sum: Long = 0
    while(currentNodes.size > 0){
        val cur = currentNodes.removeAt(0)
        if(cur.getSize() <= TARGET_SIZE){
            sum += cur.getSize()
        } 
        currentNodes.addAll(cur.subDirs)
    }
    return sum
}

fun part2(): Int {
    return 0 
}

fun solution(){
    println("No Space Left On Drive Part 1 solution: ${part1()}")
    println("No Space Left On Drive Part 2 solution: ${part2()}")
}