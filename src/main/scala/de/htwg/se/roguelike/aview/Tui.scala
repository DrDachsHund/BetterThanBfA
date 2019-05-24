package de.htwg.se.roguelike.aview

import de.htwg.se.roguelike.controller.{Controller, GameStatus}
import de.htwg.se.roguelike.util.Observer

class Tui(controller: Controller) extends Observer {

  controller.add(this)

  //State Pattern
  var state:State = new tuiMain(controller,this)
  var inventoryGameStatus: GameStatus.Value = GameStatus.LEVEL

  //GameOver Tui fürs REstarten des Games

  override def update(): Unit = println(">> \n" + controller.strategy.updateToString + "<<\n")
}