import java.net.InetAddress
import java.util.Scanner

fun main() {
    val username = System.getProperty("user.name")
    val hostname = InetAddress.getLocalHost().hostName
    val scanner = Scanner(System.`in`)

    while (true) {
        print("$username@$hostname:~$ ")
        val input = scanner.nextLine().trim()

        if (input.isEmpty()) continue
        val parts = input.split(" ")
        val command = parts[0]
        val args = parts.drop(1)

        when (command) {
            "ls", "cd" -> {
                println("Команда: $command \nАргументы: ${args.joinToString(", ")}")
            }
            "exit" -> {
                println("Выход из эмулятора.")
                return
            }
            else -> {
                println("Ошибка: неизвестная команда \"$command\"")
            }
        }
    }
}
