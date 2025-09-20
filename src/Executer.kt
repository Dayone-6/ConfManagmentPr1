import exceptions.InvalidPathException
import exceptions.NotAccessibleException
import exceptions.PathIsNotADirectoryException
import exceptions.VFSNotFoundException
import java.util.zip.ZipFile
import kotlin.system.exitProcess

class Executer(
    private var vfsPath: String,
    private var userName: String
) {
    private var currentPath = vfsPath
    private var vfs: ZipFile

    init {
        try {
            if(vfsPath.isNotEmpty()){
                vfs = ZipFile(vfsPath)
            }else{
                throw VFSNotFoundException()
            }
        }catch (e: Exception){
            e.printStackTrace()
            throw VFSNotFoundException()
        }
    }

    fun getCurrentDirectory(): String = currentPath

    private fun getPathFormatted(path: String): String{
        var formattedPath = path.replace(vfsPath, "").replace("\\", "/")
        if(formattedPath.startsWith("/")){
            formattedPath = formattedPath.substring(1)
        }
        return formattedPath
    }

    fun whoami(): Int{
        println(userName)
        return 0
    }

    fun tail(lines: Int, name: String): Int {
        val reader = vfs.getInputStream(vfs.getEntry(getPathFormatted(currentPath) + "/" + name))

        val bytes = reader.readAllBytes()
        val content = String(bytes)
        val allLines = content.lines().filter { it.isNotEmpty() }
        val startIndex = maxOf(0, allLines.size - lines)
        val tailLines = allLines.subList(startIndex, allLines.size)

        tailLines.forEach { println(it) }
        return 0
    }

    fun pwd(): Int{
        println("vfs/" + getPathFormatted(currentPath))
        return 0
    }

    fun cd(arguments: List<String>): Int  {
        var newPath = arguments.joinToString(separator = " ")
        if(newPath.startsWith("\\")){
            newPath = currentPath + newPath
        }else if(newPath == ".."){
            val currentDirectorySplitted = currentPath.split("\\").dropLast(1)
            newPath = currentDirectorySplitted.joinToString("\\")
        }
        if(!newPath.startsWith(vfsPath)){
            throw NotAccessibleException(newPath)
        }
        val file = vfs.getEntry(getPathFormatted(newPath))
        if(file != null){
            if(file.isDirectory){
                currentPath = newPath
            }else{
                throw PathIsNotADirectoryException(newPath)
            }
        }else{
            throw InvalidPathException(newPath)
        }
        return 0
    }

    fun ls(): Int {
        val files = vfs.entries().asSequence()
        val formattedPath = getPathFormatted(currentPath)
        val children = files.filter { it.name.startsWith(formattedPath) }
        println("TYPE\t\tNAME\n----------------")
        for(child in children){
            var replaced = child.name.replace(formattedPath, "")
            if(!replaced.startsWith("/") && !formattedPath.isEmpty()){
                continue
            }
            if(!replaced.startsWith("/")){
                replaced = "/$replaced"
            }
            if((replaced.count{ it == '/' } - child.isDirectory.toInt()) == 1){
                print(if(child.isDirectory){
                    "Directory"
                }else{
                    "File"
                } + "\t\t")
                println(replaced.replace("/", ""))
            }
        }
        return 0
    }

    private fun Boolean.toInt() = if (this) 1 else 0

    fun exit(): Int{
        println("Exiting from emulator")
        exitProcess(1)
    }

    fun help(): Int{
        println("\t cd".paint(33) + "\t\tGoto directory")
        println("\t ls".paint(33) + "\t\tGet info about directory")
        println("\t help".paint(33) + "\tPrint all available commands")
        return 0
    }
}