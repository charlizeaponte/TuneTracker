package edu.quinnipiac.ser210.tunetracker.api.lyrics

import edu.quinnipiac.ser210.tunetracker.api.lyrics.Run

data class Footer(
    val runs: List<Run>,
    val text: String
)