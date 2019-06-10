package de.htwg.se.roguelike.aview.gui

import de.htwg.se.roguelike.aview.tui.State
import de.htwg.se.roguelike.controller.{Controller, GameStatus}

import scala.swing.{Dimension, Panel}

case class guiInventoryArmor(controller: Controller, gui: SwingGui) extends StateGui {
  override def processInputLine(input: String): Unit = {
    input match {
      case "x" => controller.setGameStatus(GameStatus.INVENTORY)
      case "H" => controller.unEquipHelmet()
      case "C" => controller.unEquipChest()
      case "P" => controller.unEquipPants()
      case "B" => controller.unEquipBoots()
      case "G" => controller.unEquipGloves()
      case "q" =>
      case _ =>
        input.toList.filter(c => c != ' ').filter(_.isDigit).map(c => c.toString.toInt) match {
          case index :: Nil => controller.equipArmor(index)
          case _ =>
        }
    }
  }

  override def handle(): Unit = {
    val e = controller.gameStatus
    e match {
      case GameStatus.INVENTORYARMOR => gui.state = this
      case GameStatus.INVENTORY => gui.state = new guiInventoryMain(controller, gui)
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
