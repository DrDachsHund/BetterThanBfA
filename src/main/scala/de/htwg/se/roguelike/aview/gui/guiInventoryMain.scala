package de.htwg.se.roguelike.aview.gui

import de.htwg.se.roguelike.aview.tui.State
import de.htwg.se.roguelike.controller.{Controller, GameStatus}

import scala.swing.{Dimension, Panel}

case class guiInventoryMain(controller: Controller, gui: SwingGui) extends StateGui {
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


      //case "x" => controller.setGameStatus(tui.inventoryGameStatus)
      case "q" =>
      case _ =>
        print("Wrong Input!!!")

    }
  }

  override def handle(): Unit = {
    val e = controller.gameStatus
    e match {
      case GameStatus.INVENTORY => gui.state = this
      case GameStatus.LEVEL => gui.state = new guiMain(controller, gui)
      case GameStatus.FIGHT => gui.state = new guiFight(controller, gui)
      case GameStatus.INVENTORYPOTION => gui.state = new guiInventoryPotion(controller, gui)
      case GameStatus.INVENTORYARMOR => gui.state = new guiInventoryArmor(controller, gui)
      case GameStatus.INVENTORYWEAPON => gui.state = new guiInventoryWeapon(controller, gui)
      case _ =>
        print("Wrong GameStatus!!!")
    }
  }

  override def drawPanel(SCALE: Int): Panel = {
    new Panel {
      preferredSize = new Dimension(256 * SCALE, 144 * SCALE + 20)
    }
  }
}
