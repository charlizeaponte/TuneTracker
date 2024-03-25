package edu.quinnipiac.ser210.tunetracker

data class lyrics(
    val description: Description,
    val footer: Footer,
    val max_collapsed_lines: Int,
    val max_expanded_lines: Int,
    val type: String
)