package de.htwg.se.roguelike.aview.tui

import de.htwg.se.roguelike.controller._
//import de.htwg.se.roguelike.util.Observer

import scala.swing.Reactor

class Tui(controller: Controller) extends Reactor { //with Observer

  listenTo(controller)

  //controller.add(this)

  //State Pattern
  var state: State = new tuiMain(controller, this)
  var inventoryGameStatus: GameStatus.Value = GameStatus.LEVEL

  //GameOver Tui fürs REstarten des Games
  //Start screen tui

//  override def update(): Unit = println(">> \n" + controller.strategy.updateToString + "<<\n")

  reactions += {
    case _: TileChanged => {
      tuidraw()
      state.handle()
    }
    case _: LevelSizeChanged =>{
      tuidraw()
      state.handle()
    }
    case _: FightEvent => {
      tuidraw()
      state.handle()
    }
  }

  def tuidraw(): Unit = println(">> \n" + controller.strategy.updateToString + "<<\n")
}