package de.htwg.se.roguelike

import de.htwg.se.roguelike.aview.Tui
import de.htwg.se.roguelike.model.{Level, Player}

object RogueLike {

  var level = new Level(10)
  val tui = new Tui

  def main(args: Array[String]): Unit = {
    var input: String = ""

    do {
      println("Level : \n" + level.toString)
      input = readLine()
      level = tui.processInputLine(input, level)
    } while (input != "q")
  }
}
