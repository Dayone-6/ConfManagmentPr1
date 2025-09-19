import java.io.File
import java.io.FileInputStream
import java.util.TreeSet
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipInputStream
import kotlin.system.exitProcess

class Executer(
    private var vfsPath: String
) {
    private var currentPath = vfsPath
    private lateinit var vfs: ZipFile

    init {
        try {
            if(vfsPath.isNotEmpty()){
                vfs = ZipFile(vfsPath)
            }else{
                println("VFS path not passed!")
            }
        }catch (e: Exception){
            e.printStackTrace()
            println("VFS not found!")
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

    fun cd(arguments: List<String>): Int {
        var newPath = arguments.joinToString(separator = " ")
        if(newPath.startsWith("\\")){
            newPath = currentPath + newPath
        }else if(newPath == ".."){
            val currentDirectorySplitted = currentPath.split("\\")
            newPath = currentDirectorySplitted.subList(0, currentDirectorySplitted.size - 1).joinToString("\\")
        }
        if(!newPath.startsWith(vfsPath)){
            println("$newPath is not accessible!")
            return 1
        }
        val file = vfs.getEntry(getPathFormatted(newPath))
        if(file != null){
            if(file.isDirectory){
                currentPath = newPath
            }else{
                println("${newPath.replace(vfsPath, "vfs")} is not a directory")
                return 1
            }
        }else{
            println("Directory ${newPath.replace(vfsPath, "vfs")} does not exist")
            return 2
        }
        return 0
    }

    fun ls(): Int {
        val files = vfs.entries().asSequence()
        val formattedPath = getPathFormatted(currentPath)
        val children = files.filter { it.name.startsWith(formattedPath) }
        println("TYPE\t\tNAME\n----------------")
        for(child in children){
            println(child.name)
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
        println("Выход из эмулятора")
        exitProcess(1)
    }

    fun help(): Int{
        println("\t cd\tGoto directory")
        println("\t ls\tGet info about directory")
        println("\t help\tPrint all available commands")
        return 0
    }
}