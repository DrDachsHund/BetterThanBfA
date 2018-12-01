package de.htwg.se.betterthanbfa

import de.htwg.se.betterthanbfa.aview.Tui
import de.htwg.se.betterthanbfa.model.playerComponent._
import de.htwg.se.betterthanbfa.model.LevelComponent._


object BetterThanBfA {

  var player: Player = new Player("TestPlayer")
  var level: Level = new Level(player)
  val tui: Tui = new Tui

  def main(args: Array[String]): Unit = {
    var input: String = ""

    //println(player.posY)
    //println(level.player.posY)

    do {
      println("Level: \n" + level.toString)
      input = readLine()
      tui.processInputLine(input, level)
    } while (input != "q")


  }
}
