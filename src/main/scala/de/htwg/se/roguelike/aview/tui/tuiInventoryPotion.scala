package de.htwg.se.roguelike.aview.tui

import de.htwg.se.roguelike.controller.{ControllerInterface, GameStatus}
import de.htwg.se.roguelike.controller.controllerBaseImpl.Controller

case class tuiInventoryPotion(controller: ControllerInterface, tui: Tui) extends State {
  override def processInputLine(input: String): Unit = {
    input match {
      case "x" => controller.setGameStatus(GameStatus.INVENTORY)
      case "q" =>
      case _ =>
        input.toList.filter(c => c != ' ').filter(_.isDigit).map(c => c.toString.toInt) match {
          case index :: Nil => controller.usePotion(index)
          case _ =>
        }
    }
    handle()
  }

  override def handle(): Unit = {
    val e = controller.gameStatus
    e match {
      case GameStatus.INVENTORYPOTION => tui.state = this
      case GameStatus.INVENTORY => tui.state = new tuiInventoryMain(controller, tui)
      case _ =>
        print("Wrong GameStatus!!!")
    }
  }
}
