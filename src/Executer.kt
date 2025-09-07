import kotlin.system.exitProcess

class Executer {
    fun cd(arguments: List<String>): Int{
        println("Команда: cd \nАргументы: ${arguments.joinToString(", ")}")
        return 0
    }

    fun ls(arguments: List<String>): Int{
        println("Команда: ls \nАргументы: ${arguments.joinToString(", ")}")
        return 0
    }

    fun exit(): Int{
        println("Выход из эмулятора")
        exitProcess(1)
    }
}