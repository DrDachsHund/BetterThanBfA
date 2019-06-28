package de.htwg.se.roguelike.aview.tui

import de.htwg.se.roguelike.controller.GameStatus
import de.htwg.se.roguelike.controller.controllerBaseImpl.Controller

case class tuiStartScreen(controller: Controller, tui: Tui) extends State {
  def processInputLine(input: String): Unit = {
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
    handle()
  }

  override def handle(): Unit = {
    val e = controller.gameStatus
    e match {
      case GameStatus.LEVEL => tui.state = new tuiLevel(controller, tui)
      case GameStatus.STARTSCREEN => tui.state = this
      case _ =>
        print("Wrong GameStatus!!!")
    }
  }
}
