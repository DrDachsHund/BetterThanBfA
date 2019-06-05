package de.htwg.se.roguelike.aview

import de.htwg.se.roguelike.controller._
import de.htwg.se.roguelike.util.Observer

import scala.swing.Reactor

class Tui(controller: Controller) extends Reactor with Observer {

  listenTo(controller)
  controller.add(this)

  //State Pattern
  var state: State = new tuiMain(controller, this)
  var inventoryGameStatus: GameStatus.Value = GameStatus.LEVEL

  //GameOver Tui fürs REstarten des Games
  //Start screen tui

  override def update(): Unit = println(">> \n" + controller.strategy.updateToString + "<<\n")

  reactions += {
    case _: TileChanged => tuidraw()
    case _: LevelSizeChanged => tuidraw()
    case _: FightEvent => tuidraw()
  }

  def tuidraw(): Unit = println(">> \n" + controller.strategy.updateToString + "<<\n")
}