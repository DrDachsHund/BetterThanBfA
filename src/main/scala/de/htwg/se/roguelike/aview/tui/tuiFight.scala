package de.htwg.se.roguelike.aview.tui

import de.htwg.se.roguelike.controller.{ControllerInterface, GameStatus}
import de.htwg.se.roguelike.controller.controllerBaseImpl.Controller

case class tuiFight(controller: ControllerInterface, tui: Tui) extends State {
  def processInputLine(input: String): Unit = {
    input match {
      case "q" =>
      case "1" => controller.attack()
      case "2" => controller.block()
      case "3" => controller.special()
      case "r" => controller.run()
      case "i" => {
        controller.setGameStatus(GameStatus.INVENTORY)
        controller.inventoryGameStatus = GameStatus.FIGHT
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
      case GameStatus.FIGHT => tui.state = this
      case GameStatus.FIGHTSTATUS => tui.state = this
      case GameStatus.INVENTORY => tui.state = new tuiInventoryMain(controller, tui)
      case GameStatus.GAMEOVER => tui.state = new tuiGameOver(controller, tui)
      case GameStatus.PLAYERLEVELUP => tui.state = new tuiPlayerLevelUp(controller, tui)
      case GameStatus.LOOTENEMY => tui.state = new tuiLootEnemy(controller, tui)
      case _ =>
        print("Wrong GameStatus!!!")
    }
  }
}
