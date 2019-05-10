package de.htwg.se.roguelike.aview

import de.htwg.se.roguelike.controller.Controller
import de.htwg.se.roguelike.util.Observer

class TuiFight(controller: Controller) extends Tui {

  controller.add(this)

  def processInputLine(input: String): Unit = {
    input match {
      case "q" =>
      case "x" => println("topkek")
      case _ => {
        print("Wrong Input!!!")
      }
    }
  }

  override def update: Unit = println(controller.levelToString)

  override def handle(e: Event): State = {
    e match {
      case main: EventMain => this
      case fight: EventFight => new TuiFight(controller)
    }
  }
}