package de.htwg.se.roguelike.aview

import de.htwg.se.roguelike.controller.{Controller, GameStatus}

class tuiInventoryWeapon(controller: Controller, tui: Tui) extends State {
  override def processInputLine(input: String): Unit = {
    input match {
      case "x" => controller.setGameStatus(GameStatus.INVENTORY)
      case "R" => controller.unEquipRightHand()
      case "L" => controller.unEquipLeftHand()
      case "q" =>
      case _ =>
        input.toList.filter(c => c != ' ').filter(_.isDigit).map(c => c.toString.toInt) match {
          case hand :: index :: Nil => controller.equipWeapon(hand, index)
          case _ =>
        }
    }
    handle()
  }

  override def handle(): Unit = {
    val e = controller.gameStatus
    e match {
      case GameStatus.INVENTORYWEAPON => tui.state = this
      case GameStatus.INVENTORY => tui.state = new tuiInventoryMain(controller, tui)
      case _ =>
        print("Wrong GameStatus!!!")
    }
  }
}
