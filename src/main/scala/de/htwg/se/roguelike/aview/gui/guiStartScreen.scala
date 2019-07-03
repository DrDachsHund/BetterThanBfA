package de.htwg.se.roguelike.aview.gui

import java.awt.{Color, Font, Graphics2D}

import de.htwg.se.roguelike.controller.{ControllerInterface, GameStatus}
import de.htwg.se.roguelike.controller.controllerBaseImpl.Controller

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

      val newGame = new Button("newGame")
      newGame.peer.setBounds(64 * SCALE, 34 * SCALE, 128 * SCALE, 20 * SCALE)
      listenTo(newGame)
      contents += newGame

      val devTest = new Button("devTest")
      devTest.peer.setBounds(64 * SCALE, 94 * SCALE, 128 * SCALE, 20 * SCALE)
      listenTo(devTest)
      contents += devTest


      reactions += {
        case ButtonClicked(t) if t == newGame => {
          controller.createRandomLevel()
          controller.setGameStatus(GameStatus.LEVEL)
        }
        case ButtonClicked(t) if t == devTest => {
          controller.createLevel()
          controller.setGameStatus(GameStatus.LEVEL)
        }
      }


      override def paintComponent(g: Graphics2D): Unit = {


      }

    }
    panel
  }
}