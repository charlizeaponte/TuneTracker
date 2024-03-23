package edu.quinnipiac.ser210.tunetracker

data class Result(
    val artist: String,
    val duration: String,
    val isExplicit: Boolean,
    val songImage: String,
    val title: String,
    val videoId: String,
    val lyrics: String
)