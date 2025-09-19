fun printErrorLine(message: String) {
    val red = "\u001b[31m"
    val reset = "\u001b[0m"
    println("$red$message$reset")
}

fun String.paint(ansiColor: Int) = "\u001b[${ansiColor}m$this\u001B[0m"