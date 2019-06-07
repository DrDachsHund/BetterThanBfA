package de.htwg.se.roguelike.aview.gui

import de.htwg.se.roguelike.aview.State
import de.htwg.se.roguelike.controller.{Controller, GameStatus}

import scala.swing.{Button, Panel}

case class guiFight(controller: Controller, gui: SwingGui) extends StateGui {
  override def processInputLine(input: String): Unit = {
    input match {
      case "q" =>
      case "1" => controller.attack()
      case "2" => controller.block()
      case "3" => controller.special()
      case "r" => controller.run()
      case "i" =>
        controller.setGameStatus(GameStatus.INVENTORY)
        //tui.inventoryGameStatus = GameStatus.FIGHT
      case _ =>
        print("Wrong Input!!!")
    }
  }

  override def handle(): Unit = {
    val e = controller.gameStatus
    e match {
      case GameStatus.LEVEL => gui.state = new guiMain(controller, gui)
      case GameStatus.FIGHT => gui.state = this
      case GameStatus.FIGHTSTATUS => gui.state = this
      case GameStatus.INVENTORY => gui.state = new guiInventoryMain(controller, gui)
      case GameStatus.GAMEOVER => gui.state = new guiGameOver(controller, gui)
      case GameStatus.PLAYERLEVELUP => gui.state = new guiPlayerLevelUp(controller, gui)
      case GameStatus.LOOTENEMY => gui.state = new guiLootEnemy(controller, gui)
      case _ =>
        print("Wrong GameStatus!!!")
    }
  }

  override def drawPanel(Scale: Int): Panel = ???

}
