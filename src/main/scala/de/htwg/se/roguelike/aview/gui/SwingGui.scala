package de.htwg.se.roguelike.aview.gui

import java.awt.image.BufferedImage
import java.awt.{BorderLayout, Canvas, Graphics2D}
import java.io.File

import de.htwg.se.roguelike.controller.{Controller, FightEvent, LevelSizeChanged, TileChanged}
import javax.imageio.ImageIO
import javax.swing.ImageIcon

import scala.swing._

class SwingGui(controller: Controller) extends Frame {

  listenTo(controller)

  //---GUI--

  title = "Pog Game"


  val img = ImageIO.read(getClass.getResource("Test.png"))

  val g = img.getGraphics

  val panel = new Panel {
    preferredSize = new Dimension(1000,1000)
    override def paint(g: Graphics2D): Unit = {
          g.drawImage(img,0,0,null)
    }
  }
  contents = new BorderPanel {
    add(panel,BorderPanel.Position.Center)
  }


  panel.repaint()

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
