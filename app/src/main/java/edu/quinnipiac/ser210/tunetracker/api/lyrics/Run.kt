package edu.quinnipiac.ser210.tunetracker.api.lyrics

data class Run(
    val bold: Boolean,
    val italics: Boolean,
    val strikethrough: Boolean,
    val text: String
)