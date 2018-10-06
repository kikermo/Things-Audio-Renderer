package org.kikermo.thingsaudio.core.utils

fun Int.toFormatedSeconds(): String {
    val seconds = this
    val remSeconds = seconds % 60
    var min = seconds / 60
    val hour = min / 60
    min %= 60

    val formatted = String.format("%02d:%02d", min, remSeconds)

    return if (hour > 0) hour.toString() + ":" + formatted else formatted
}