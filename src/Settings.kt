import java.io.File

class Settings(
    private val args: Array<String>,
) {
    private lateinit var arguments: Map<String, String>
    private lateinit var config: Map<String, String>

    init {
        try {
            arguments = args.toList().chunked(2).associate { it[0] to it[1] }
            val file = File("C:\\Users\\leoni\\OneDrive\\Desktop\\University\\Conf managment\\configurationManagmentPr1\\src\\settings.csv")
            config = file.readLines().map { it.split(",") }.associate { it[0] to it[1] }

            println("All passed arguments")
            if(arguments.isEmpty()){
                println("No arguments passed")
            }

            for(arg in arguments){
                println("${arg.key}\t${arg.value}")
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun getAllArguments(): Map<String, String> = arguments

    fun getSetting(name: String): String? {
        if(arguments.isNotEmpty()){
            if(arguments.containsKey(name)){
                return arguments[name]
            }
        }
        return config[name]
    }
}