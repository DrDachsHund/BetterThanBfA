package de.htwg.se.roguelike.aview.tui

import de.htwg.se.roguelike.controller.{ControllerInterface, GameStatus}
import de.htwg.se.roguelike.controller.controllerBaseImpl.Controller

case class tuiInventoryMain(controller: ControllerInterface, tui: Tui) extends State {
  override def processInputLine(input: String): Unit = {
    input match {
      case "1" => controller.setGameStatus(GameStatus.INVENTORYPOTION)
      case "2" => controller.setGameStatus(GameStatus.INVENTORYWEAPON)
      case "3" => controller.setGameStatus(GameStatus.INVENTORYARMOR)

      case "H" => controller.unEquipHelmet()
      case "C" => controller.unEquipChest()
      case "P" => controller.unEquipPants()
      case "B" => controller.unEquipBoots()
      case "G" => controller.unEquipGloves()

      case "R" => controller.unEquipRightHand()
      case "L" => controller.unEquipLeftHand()


      case "x" => controller.setGameStatus(controller.inventoryGameStatus)
      case "q" =>
      case _ =>
        print("Wrong Input!!!")

    }
    handle()
  }

  override def handle(): Unit = {
    val e = controller.gameStatus
    e match {
      case GameStatus.INVENTORY => tui.state = this
      case GameStatus.LEVEL => tui.state = new tuiLevel(controller, tui)
      case GameStatus.FIGHT => tui.state = new tuiFight(controller, tui)
      case GameStatus.INVENTORYPOTION => tui.state = new tuiInventoryPotion(controller, tui)
      case GameStatus.INVENTORYARMOR => tui.state = new tuiInventoryArmor(controller, tui)
      case GameStatus.INVENTORYWEAPON => tui.state = new tuiInventoryWeapon(controller, tui)
      case _ =>
        print("Wrong GameStatus!!!")
    }
  }
}
