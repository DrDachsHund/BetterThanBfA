package de.htwg.se.betterthanbfa.aview

import de.htwg.se.betterthanbfa.controller.Controller
import de.htwg.se.betterthanbfa.util.Observer

class Tui(controller: Controller) extends Observer {

  controller.add(this)

  def processInputLine(input: String): Unit = {
    input match {
      case "q" =>
      case "n" => controller.createLevel(10)
      case "w" => controller.move(input)
      case "s" => controller.move(input)
      case "a" => controller.move(input)
      case "d" => controller.move(input)
      case "i" => println(controller.playerToString)
      case _ => println("FalscheEingabe")
    }
  }

  override def update: Boolean = {println(controller.levelToString);true}

}
