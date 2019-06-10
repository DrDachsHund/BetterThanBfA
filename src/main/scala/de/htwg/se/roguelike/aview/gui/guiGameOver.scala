package de.htwg.se.roguelike.aview.gui

import de.htwg.se.roguelike.controller.{Controller, GameStatus}

import scala.swing.{Dimension, Panel}

case class guiGameOver(controller: Controller, gui: SwingGui) extends StateGui {
  override def processInputLine(input: String): Unit = {
    input match {
      case "q" => System.exit(0)
      case "n" => controller.newGame()
      case _ => println("Wrong Input!!!")
    }
  }
  override def handle(): Unit = {
    val e = controller.gameStatus
    e match {
      case GameStatus.GAMEOVER => gui.state = this
      case GameStatus.LEVEL => gui.state = new guiMain(controller, gui)
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
