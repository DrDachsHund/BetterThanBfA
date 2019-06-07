package de.htwg.se.roguelike.aview.gui

import de.htwg.se.roguelike.aview.State
import de.htwg.se.roguelike.controller.{Controller, GameStatus}

import scala.swing.Panel

case class guiLootEnemy(controller: Controller, gui: SwingGui) extends StateGui {
  override def processInputLine(input: String): Unit = {
    input match {
      case "q" =>
      case "x" => controller.setGameStatus(GameStatus.LEVEL)
      case _ =>
        input.toList.filter(c => c != ' ').filter(_.isDigit).map(c => c.toString.toInt) match {
          case index :: Nil => controller.lootingEnemy(index)
          case _ =>
        }
    }
  }

  override def handle(): Unit = {
    val e = controller.gameStatus
    e match {
      case GameStatus.LEVEL => gui.state = new guiMain(controller, gui)
      case GameStatus.LOOTENEMY => gui.state = this
      case _ =>
        print("Wrong GameStatus!!!")
    }
  }

  override def drawPanel(Scale: Int): Panel = ???
}
