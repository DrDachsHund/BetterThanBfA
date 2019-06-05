package de.htwg.se.roguelike.aview.gui

import de.htwg.se.roguelike.controller.{Controller, FightEvent, LevelSizeChanged, TileChanged}

import scala.swing._

class SwingGui(controller: Controller) extends Frame {

  listenTo(controller)


  title = "Pog Game"
  preferredSize = new Dimension(720, 480)
  contents = Button("Press me, please") {
    controller.createRandomLevel()
  }
  visible = true


  reactions += {
    case _: TileChanged => redraw
    case _: LevelSizeChanged => redraw
    case _: FightEvent => redraw
  }

  def redraw: Unit = {
    //zeugs

    repaint
  }
}
