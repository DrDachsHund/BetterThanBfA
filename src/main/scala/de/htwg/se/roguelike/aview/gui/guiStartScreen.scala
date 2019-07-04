package de.htwg.se.roguelike.aview.gui

import java.awt.{Color, Font, Graphics2D}

import de.htwg.se.roguelike.controller.{ControllerInterface, GameStatus}
import de.htwg.se.roguelike.controller.controllerBaseImpl.Controller
import javax.swing.ImageIcon

import scala.swing.event.ButtonClicked
import scala.swing.{Button, Dimension, FileChooser, FlowPanel, Panel}

case class guiStartScreen(controller: ControllerInterface, gui: SwingGui) extends StateGui {
  override def processInputLine(input: String): Unit = {
    input match {
      case "q" =>
      case "n" => {
        controller.createRandomLevel()
        controller.setGameStatus(GameStatus.LEVEL)
      }
      case "d" => {
        controller.createLevel()
        controller.setGameStatus(GameStatus.LEVEL)
      }
      case _ =>
        print("Wrong Input!!!")
    }
  }

  override def handle(): Unit = {
    val e = controller.gameStatus
    e match {
      case GameStatus.STARTSCREEN => gui.state = this
      case GameStatus.LEVEL => gui.state = new guiLevel(controller, gui)
      case _ =>
        print("Wrong GameStatus!!!")
    }
  }

  override def drawPanel(SCALE: Int): Panel = {
    val panel = new FlowPanel() {
      preferredSize = new Dimension(256 * SCALE, 144 * SCALE)

      peer.setLayout(null)

      val newGameButtonImage = new SpriteSheet("resources/newGame.png")
      val newGameIcon = new ImageIcon(newGameButtonImage.getImage().getScaledInstance(256 * SCALE, 45 * SCALE, java.awt.Image.SCALE_SMOOTH))

      val newGame = new Button()
      newGame.peer.setBounds(0 * SCALE, 102 * SCALE, 256 * SCALE, 45 * SCALE)
      newGame.peer.setIcon(newGameIcon)
      listenTo(newGame)
      contents += newGame


      reactions += {
        case ButtonClicked(t) if t == newGame => {
          controller.createRandomLevel()
          controller.setGameStatus(GameStatus.LEVEL)
        }
      }


      override def paintComponent(g: Graphics2D): Unit = {

        val startBackgroundSpriteSheet = new SpriteSheet("resources/startscreen.png")
        val startBackground = startBackgroundSpriteSheet.getImage()
        g.drawImage(startBackground, 0, 0, 256 * SCALE, 144 * SCALE, null)

      }

    }
    panel
  }
}