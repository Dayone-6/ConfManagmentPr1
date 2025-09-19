package exceptions

class InvalidPathException(path: String) : Exception("Path $path is invalid")