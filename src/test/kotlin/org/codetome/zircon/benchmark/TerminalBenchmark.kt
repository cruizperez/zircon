package org.codetome.zircon.benchmark

import org.codetome.zircon.Position
import org.codetome.zircon.api.CP437TilesetResource
import org.codetome.zircon.api.TerminalBuilder
import org.codetome.zircon.api.TextColorFactory
import org.codetome.zircon.Size

fun main(args: Array<String>) {
    val terminal = TerminalBuilder.newBuilder()
            .initialTerminalSize(SIZE)
            .font(CP437TilesetResource.WANDERLUST_16X16.asJava2DFont())
            .buildTerminal()
    terminal.setCursorVisible(false)

    val charCount = WIDTH * HEIGHT
    val chars = listOf('a', 'b')
    val bgColors = listOf(TextColorFactory.fromString("#223344"), TextColorFactory.fromString("#112233"))
    val fgColors = listOf(TextColorFactory.fromString("#ffaaff"), TextColorFactory.fromString("#aaffaa"))
    var avgMs = 0.0
    var measurements = 0

    var currIdx = 0
    var loopCount = 0
    while (true) {
        val start = System.nanoTime()
        terminal.setBackgroundColor(bgColors[currIdx])
        terminal.setForegroundColor(fgColors[currIdx])
        (0..charCount).forEach {
            terminal.putCharacter(chars[currIdx])
        }
        terminal.flush()
        terminal.putCursorAt(Position.DEFAULT_POSITION)
        currIdx = if (currIdx == 0) 1 else 0
        loopCount++
        val end = System.nanoTime()
        var totalMs: Double = (end - start).toDouble() / 1000.toDouble() / 1000.toDouble()
        avgMs = (avgMs * measurements + totalMs) / ++measurements
        if (avgMs == 0.0) {
            avgMs = 0.0001
        }
        if (totalMs == 0.0) {
            totalMs = 0.0001
        }
        if (measurements % 10 == 0) {
            println("Current FPS is: ${1000 / totalMs}. Average fps is ${1000 / avgMs}. Render time is ${totalMs}.")
        }
    }
}

val WIDTH = 60
val HEIGHT = 30
val SIZE = Size(WIDTH, HEIGHT)