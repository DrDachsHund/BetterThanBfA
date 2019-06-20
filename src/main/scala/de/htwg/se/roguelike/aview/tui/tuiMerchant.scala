package de.htwg.se.roguelike.aview.tui

import de.htwg.se.roguelike.controller.{Controller, GameStatus}

case class tuiMerchant(controller: Controller, tui: Tui) extends State {
  def processInputLine(input: String): Unit = {
    input match {
      case "q" =>
      case "x" => controller.run()
      case _ =>
        input.toList.filter(c => c != ' ').filter(_.isDigit).map(c => c.toString.toInt) match {
          case index :: Nil => controller.lootingEnemy(index)
          case _ =>
        }
    }
    handle()
  }

  override def handle(): Unit = {
    val e = controller.gameStatus
    e match {
      case GameStatus.LEVEL => tui.state = new tuiLevel(controller, tui)
      case GameStatus.MERCHANT => tui.state = this
      case _ =>
        print("Wrong GameStatus!!!")
    }
  }
}
