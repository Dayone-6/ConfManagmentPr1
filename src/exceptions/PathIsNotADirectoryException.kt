package exceptions

class PathIsNotADirectoryException(path: String) : Exception("$path is not a directory")