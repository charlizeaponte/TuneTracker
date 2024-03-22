package edu.quinnipiac.ser210.tunetracker

data class Result(
    val author: String,
    val duration: String,
    val isExplicit: Boolean,
    val thumbnail: String,
    val title: String,
    val videoId: String
)