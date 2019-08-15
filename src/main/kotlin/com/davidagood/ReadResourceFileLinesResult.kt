package com.davidagood

data class ReadResourceFileLinesResult(val resourceRelativeFilePath: String,
                                       val didReadSuccessfully: Boolean,
                                       val errorMessage: String?,
                                       val lines: List<String>?)