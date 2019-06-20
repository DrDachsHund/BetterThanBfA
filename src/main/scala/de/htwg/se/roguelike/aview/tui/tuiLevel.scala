package de.htwg.se.roguelike.aview.tui

import de.htwg.se.roguelike.controller.{Controller, GameStatus}

class tuiLevel(controller: Controller, tui: Tui) extends State {
  def processInputLine(input: String): Unit = {
    input match {
      case "q" =>
      case "r" => controller.createRandomLevel()
      case "n" => controller.createLevel()
      case "w" => controller.moveUp()
      case "a" => controller.moveLeft()
      case "s" => controller.moveDown()
      case "d" => controller.moveRight()
      case "z" => controller.undo()
      case "y" => controller.redo()
      case "i" =>
        controller.setGameStatus(GameStatus.INVENTORY)
        controller.inventoryGameStatus = GameStatus.LEVEL
      case _ =>
        print("Wrong Input!!!")
    }
    handle()
  }

  override def handle(): Unit = {
    val e = controller.gameStatus
    e match {
      case GameStatus.LEVEL => tui.state = this
      case GameStatus.FIGHT => tui.state = new tuiFight(controller, tui)
      case GameStatus.INVENTORY => tui.state = new tuiInventoryMain(controller, tui)
      case GameStatus.MERCHANT => tui.state = new tuiMerchant(controller, tui)
      case _ =>
        print("Wrong GameStatus!!!")
    }
  }
}