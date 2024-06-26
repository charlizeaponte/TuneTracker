package edu.quinnipiac.ser210.tunetracker.api.song

import java.io.Serializable


data class Song(
    val author: String,
    val duration: String,
    val isExplicit: Boolean,
    val thumbnail: String,
    val title: String,
    val videoId: String,
) : Serializable