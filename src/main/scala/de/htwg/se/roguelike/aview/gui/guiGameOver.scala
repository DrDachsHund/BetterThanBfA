package de.htwg.se.roguelike.aview.gui

import java.awt.{Color, Font, Graphics2D}

import de.htwg.se.roguelike.controller.{Controller, GameStatus}

import scala.swing.{Button, Dimension, Panel}

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
      case GameStatus.LEVEL => gui.state = new guiLevel(controller, gui)
      case _ =>
        print("Wrong GameStatus!!!")
    }
  }

  override def drawPanel(SCALE: Int): Panel = {
    new Panel {
      preferredSize = new Dimension(256 * SCALE, 144 * SCALE + 20)

      val gameOver = new SpriteSheet("./resources/GameOver.png").getImage()

      override def paint(g: Graphics2D): Unit = {
        g.drawImage(gameOver, 0, 0, 256 * SCALE, 144 * SCALE, null)
        g.setColor(Color.WHITE)
        g.setFont(new Font("TimesRoman", Font.BOLD, 20 * SCALE))
        g.drawString("" + controller.player.getScore(controller.lvlDepth), 125 * SCALE, 75 * SCALE)
      }

    }
  }
}
