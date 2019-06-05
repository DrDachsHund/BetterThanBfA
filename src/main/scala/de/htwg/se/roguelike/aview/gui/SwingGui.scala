package de.htwg.se.roguelike.aview.gui

import java.awt.{Canvas, Graphics2D}

import de.htwg.se.roguelike.controller.{Controller, FightEvent, LevelSizeChanged, TileChanged}
import javax.imageio.ImageIO

import scala.swing._

class SwingGui(controller: Controller) extends Frame {

  listenTo(controller)

  //---GUI--

  title = "Pog Game"

  val img = ImageIO.read(getClass.getResource("./Test.png"))

  val ig:Graphics2D = img.createGraphics()
  val c = new Canvas()

  contents = new BoxPanel(Orientation.Vertical) {
    contents += new Label("Test") {

    }
  }

  //--GUI--

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
