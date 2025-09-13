import java.io.File
import kotlin.system.exitProcess

class Executer {
    private var currentDirectory = "C:\\Users\\leoni"

    fun getCurrentDirectory(): String = currentDirectory

    fun cd(arguments: List<String>): Int {
        val newPath = arguments.joinToString(separator = " ")
        val file = File(newPath)
        if(file.exists()){
            if(file.isDirectory){
                currentDirectory = newPath
            }else{
                println("$newPath is not a directory")
                return 1
            }
        }else{
            println("Directory $newPath does not exist")
            return 2
        }
//        println("Команда: cd \nАргументы: ${arguments.joinToString(", ")}")
        return 0
    }

    fun ls(): Int {
        val directory = File(currentDirectory)
        val files = directory.listFiles()
        files?.forEach { file ->
            println("${if(file.isDirectory){"Directory"}else{"File\t"}}\t${file.name}")
        }
//        println("Команда: ls \nАргументы: ${arguments.joinToString(", ")}")
        return 0
    }

    fun exit(): Int{
        println("Выход из эмулятора")
        exitProcess(1)
    }

    fun help(): Int{
        println("\t cd Перейти в директорию")
        println("\t ls Получить информацию о текущей директории")
        println("\t help Вывести все доступные команды")
        return 0
    }
}