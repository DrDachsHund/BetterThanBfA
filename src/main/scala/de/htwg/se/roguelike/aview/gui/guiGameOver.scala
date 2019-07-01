package de.htwg.se.roguelike.aview.gui

import java.awt.{Color, Font, Graphics2D}

import de.htwg.se.roguelike.controller.{ControllerInterface, GameStatus}
import de.htwg.se.roguelike.controller.controllerBaseImpl.Controller
import javax.swing.ImageIcon

import scala.swing.event.ButtonClicked
import scala.swing.{Button, Dimension, FlowPanel, Panel}

case class guiGameOver(controller: ControllerInterface, gui: SwingGui) extends StateGui {
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

    val exitButtonImage = new SpriteSheet("resources/gameOverExitButtonIcon.png")
    val exitIcon = new ImageIcon(exitButtonImage.getImage().getScaledInstance(36 * SCALE, 20 * SCALE, java.awt.Image.SCALE_SMOOTH))

    val newGameButtonImage = new SpriteSheet("resources/newGameButtonIcon.png")
    val newGameIcon = new ImageIcon(newGameButtonImage.getImage().getScaledInstance(100 * SCALE, 21 * SCALE, java.awt.Image.SCALE_SMOOTH))

    new FlowPanel() {
      peer.setLayout(null)

      preferredSize = new Dimension(256 * SCALE, 144 * SCALE)

      val gameOver = new SpriteSheet("resources/GameOver.png").getImage()

      val newGameButoon = new Button()
      newGameButoon.peer.setBounds(0 * SCALE, 123 * SCALE, 100 * SCALE, 21 * SCALE)
      newGameButoon.peer.setIcon(newGameIcon)
      listenTo(newGameButoon)
      contents += newGameButoon

      val exit = new Button()
      exit.peer.setBounds(220 * SCALE, 124 * SCALE, 36 * SCALE, 20 * SCALE)
      exit.peer.setIcon(exitIcon)
      listenTo(exit)
      contents += exit

      reactions += {
        case ButtonClicked(b) if b == exit => System.exit(0)
        case ButtonClicked(b) if b == newGameButoon => controller.newGame()
      }

      override def paintComponent(g: Graphics2D): Unit = {
        g.drawImage(gameOver, 0, 0, 256 * SCALE, 144 * SCALE, null)
        g.setColor(Color.WHITE)
        g.setFont(new Font("TimesRoman", Font.BOLD, 20 * SCALE))
        g.drawString("Score: " + controller.player.getScore(controller.lvlDepth), 5 * SCALE, 55 * SCALE)
        g.drawString("Kill-Count: " + controller.player.killCounter, 5 * SCALE, 75 * SCALE)
        g.drawString("Level-Depth: " + controller.lvlDepth, 5 * SCALE, 95 * SCALE)
        g.drawString("Player-Level: " + controller.player.lvl, 5 * SCALE, 115 * SCALE)
      }

    }
  }
}
