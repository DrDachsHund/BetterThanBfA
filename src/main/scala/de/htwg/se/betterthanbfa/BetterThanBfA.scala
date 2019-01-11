package de.htwg.se.betterthanbfa

import de.htwg.se.betterthanbfa.aview.Tui
import de.htwg.se.betterthanbfa.controller.Controller
import de.htwg.se.betterthanbfa.model.playerComponent._
import de.htwg.se.betterthanbfa.model.LevelComponent._


object BetterThanBfA {

  var player: Player = new Player("TestPlayer")
  var level: Level = new Level(player)
  val controller = new Controller(level)
  val tui: Tui = new Tui(controller)
  controller.notifyObservers

  def main(args: Array[String]): Unit = {
    var input: String = ""
    if (!input.isEmpty) tui.processInputLine(input)
    else do {
      input = readLine()
      tui.processInputLine(input)
    } while (input != "q" && player.isAlive)

    println("Game Over")

  }
}
