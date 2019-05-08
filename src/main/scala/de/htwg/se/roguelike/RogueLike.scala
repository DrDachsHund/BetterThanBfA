package de.htwg.se.roguelike

import de.htwg.se.roguelike.aview.Tui
import de.htwg.se.roguelike.controller.Controller
import de.htwg.se.roguelike.model.{Enemy, Level, Player}

object RogueLike {

  val controller = new Controller(player = new Player(name = "Player",posX = 5, posY = 5)
    ,enemies = Vector(new Enemy(name = "TestE1",posX = 0, posY = 0),
    new Enemy(name = "TestE2",posX = 1, posY = 0),
    new Enemy(name = "TestE3",posX = 0, posY = 1)),
    level = new Level(10))
  val tui = new Tui(controller)
  controller.notifyObservers


  def main(args: Array[String]): Unit = {
    var input: String = ""
    do {
      input = readLine()
      tui.processInputLine(input)
    } while (input != "q")
  }
}
