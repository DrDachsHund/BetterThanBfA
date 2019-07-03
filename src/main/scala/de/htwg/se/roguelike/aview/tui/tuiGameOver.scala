package de.htwg.se.roguelike.aview.tui

import de.htwg.se.roguelike.controller.{ControllerInterface, GameStatus}
import de.htwg.se.roguelike.controller.controllerBaseImpl.Controller

case class tuiGameOver(controller: ControllerInterface, tui: Tui) extends State {
  def processInputLine(input: String): Unit = {
    input match {
      case "q" =>
      case "n" => controller.newGame()
      case _ => println("Wrong Input!!!")
    }
    handle()
  }

  override def handle(): Unit = {
    val e = controller.gameStatus
    e match {
      case GameStatus.GAMEOVER => tui.state = this
      case GameStatus.LEVEL => tui.state = new tuiLevel(controller, tui)
      case _ =>
        print("Wrong GameStatus!!!")
    }
  }
}
