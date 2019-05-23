package de.htwg.se.roguelike.aview

import de.htwg.se.roguelike.controller.{Controller, GameStatus}

class tuiFight(controller: Controller,tui: Tui) extends State {
  def processInputLine(input: String): Unit = {
    input match {
      case "q" =>
      case "1" => controller.attack
      case "i" => {
        controller.setGameStatus(GameStatus.INVENTORY)
        tui.inventoryGameStatus = GameStatus.FIGHT
      }
      case _ => {
        print("Wrong Input!!!")
      }
    }
    handle()
  }

  override def handle(): Unit = {
    val e = controller.gameStatus
    e match {
      case GameStatus.LEVEL => tui.state = new tuiMain(controller,tui)
      case GameStatus.FIGHT => tui.state = this
      case GameStatus.FIGHTSTATUS => tui.state = this
      case GameStatus.INVENTORY => tui.state = new tuiInventoryMain(controller,tui)
      case GameStatus.GAMEOVER => println("IS VORBEI MA DUDE")
      case _ => {
        print("Wrong GameStatus!!!")
      }
    }
  }
}
