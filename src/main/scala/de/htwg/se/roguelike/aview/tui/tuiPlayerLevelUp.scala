package de.htwg.se.roguelike.aview.tui

import de.htwg.se.roguelike.controller.{Controller, GameStatus}

case class tuiPlayerLevelUp(controller: Controller, tui: Tui) extends State {
  def processInputLine(input: String): Unit = {
    input match {
      case "q" =>
      case "1" => controller.lvlUpHealth()
      case "2" => controller.lvlUpMana()
      case "3" => controller.lvlUpAttack()
      case _ =>
        print("Wrong Input!!!")
    }
    handle()
  }

  override def handle(): Unit = {
    val e = controller.gameStatus
    e match {
      case GameStatus.LOOTENEMY => tui.state = new tuiLootEnemy(controller, tui)
      case GameStatus.PLAYERLEVELUP => tui.state = this
      case _ =>
        print("Wrong GameStatus!!!")
    }
  }
}