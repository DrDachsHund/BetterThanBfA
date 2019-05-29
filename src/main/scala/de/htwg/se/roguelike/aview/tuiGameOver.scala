package de.htwg.se.roguelike.aview

import de.htwg.se.roguelike.controller.{Controller, GameStatus}

case class tuiGameOver(controller: Controller, tui: Tui) extends State {
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
      case GameStatus.LEVEL => tui.state = new tuiMain(controller, tui)
      case _ =>
        print("Wrong GameStatus!!!")
    }
  }
}
