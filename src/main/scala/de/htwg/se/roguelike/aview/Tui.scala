package de.htwg.se.roguelike.aview

import de.htwg.se.roguelike.controller.Controller
import de.htwg.se.roguelike.util.Observer

class Tui(controller: Controller) extends Observer {

  controller.add(this)

  def processInputLine(input: String): Unit = {

    input match {
      case "q" =>
      case "r" => controller.createRandomLevel
      case "n" => controller.createLevel
      case "i" =>
        val interaction = controller.interaction
        if (interaction) println("Player interacts with Enemy") else println("No interaction found")
      case "w" => controller.moveUp
      case "a" => controller.moveLeft
      case "s" => controller.moveDown
      case "d" => controller.moveRight
      case _ => {
        print("Wrong Input!!!")
      }
    }
  }

  override def update: Unit = println(controller.levelToString)
}