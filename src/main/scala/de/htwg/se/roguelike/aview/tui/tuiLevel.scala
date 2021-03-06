package de.htwg.se.roguelike.aview.tui

import de.htwg.se.roguelike.controller.{ControllerInterface, GameStatus}
import de.htwg.se.roguelike.controller.controllerBaseImpl.Controller

case class tuiLevel(controller: ControllerInterface, tui: Tui) extends State {
  def processInputLine(input: String): Unit = {
    input match {
      case "q" =>
      case "r" => controller.createRandomLevel()
      case "w" => controller.moveUp()
      case "a" => controller.moveLeft()
      case "s" => controller.moveDown()
      case "d" => controller.moveRight()
      case "z" => controller.undo()
      case "y" => controller.redo()
      case "f" => controller.save()
      case "l" => controller.load()
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
      case GameStatus.BOSSFIGHT => tui.state = new tuiFight(controller, tui)
      case GameStatus.INVENTORY => tui.state = new tuiInventoryMain(controller, tui)
      case GameStatus.MERCHANT => tui.state = new tuiMerchant(controller, tui)
      case GameStatus.CRATE => tui.state = new tuiCrate(controller, tui)
      case _ =>
        print("Wrong GameStatus!!!")
    }
  }
}