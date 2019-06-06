package de.htwg.se.roguelike.aview.gui

import java.awt.event.{KeyEvent, KeyListener}
import java.awt.image.BufferedImage
import java.awt.{BorderLayout, Canvas, Graphics2D}
import java.io.IOException

import de.htwg.se.roguelike.controller.{Controller, FightEvent, LevelSizeChanged, TileChanged}
import javax.imageio.ImageIO
import javax.swing.ImageIcon

import scala.swing.Swing.LineBorder
import scala.swing._
import scala.swing.event.Key

class SwingGui(controller: Controller) extends Reactor {

  listenTo(controller)

  //---GUI--


  val frame = new MainFrame()
  val SCALE = 2 // eig in controller but for testing here

  frame.title = "Pog Game"
  setSize

  frame.peer.requestFocus()

  frame.contents = new BorderPanel {
    add(new GPanelLevel(controller, SCALE), BorderPanel.Position.Center)
  }

  frame.menuBar = new GMenueBar(controller)

  //--GUI--

  frame.peer.setResizable(false)
  frame.peer.setLocationRelativeTo(null)
  frame.visible = true


  reactions += {
    case _: TileChanged => redraw
    case _: LevelSizeChanged => redraw
    case _: FightEvent => redraw
  }

  def redraw(): Unit = {
    //zeugs

    frame.repaint
  }

  def closeOperation() {
    System.exit(0)
  }

  def setSize(): Unit = frame.preferredSize = new Dimension(480 * SCALE, 337 * SCALE)

  def level = new GridPanel(10, 10) {
    border = LineBorder(java.awt.Color.BLACK, 1)
    background = java.awt.Color.BLACK
    for {
      outerRow <- 0 until 10
      outerColumn <- 0 until 10
    } {
      contents += new GridPanel(10, 10) {
        border = LineBorder(java.awt.Color.BLACK, 1)
      }
    }
  }

  frame.peer.addKeyListener(new KeyListener() {
    def keyPressed(e: KeyEvent) {
      println("key pressed")
    }

    def keyReleased(e: KeyEvent) {
      println("key released")
    }

    def keyTyped(e: KeyEvent) {
      println("key typed")
    }
  })


}


private class GMenueBar(controller: Controller) extends MenuBar {
  contents += new Menu("File") {
    mnemonic = Key.F //Dose not work
    contents += new MenuItem(Action("New") {
      controller.createLevel()
    })
    contents += new MenuItem(Action("Random") {
      controller.createRandomLevel()
    })
    contents += new MenuItem(Action("Quit") {
      System.exit(0)
    })
  }
  contents += new Menu("Edit") {
    mnemonic = Key.E
    contents += new MenuItem(Action("Undo") {
      controller.undo
    })
    contents += new MenuItem(Action("Redo") {
      controller.redo
    })
  }
  contents += new Menu("Options") {
    mnemonic = Key.O
    contents += new MenuItem(Action("NO") {})
    contents += new MenuItem(Action("OPTIONS") {})
    contents += new MenuItem(Action("LUL") {})
    contents += new MenuItem(Action("W") {})
  }
}

private class GPanelLevel(controller: Controller, SCALE: Int) extends Panel {
  //val img = ImageIO.read(getClass.getResource("Test.png"))

  val backgroundSpriteSheet = new SpriteSheet("16bitSpritesBackground.png")

  val img = backgroundSpriteSheet.getSprite(16, 0)

  val g = img.getGraphics

  preferredSize = new Dimension(480 * SCALE, 320 * SCALE)

  override def paint(g: Graphics2D): Unit = {

    for (x <- 0 to controller.level.map.sizeX-1) {
      for (y <- 0 to controller.level.map.sizeY-1) {
        g.drawImage(img,y * 16 * SCALE,x * 16 * SCALE, 16 * SCALE, 16 * SCALE, null)
      }
    }

  }


  repaint()
}

private class SpriteSheet(val path: String) {

  private var sheet: BufferedImage = _

  try
    sheet = ImageIO.read(getClass.getResourceAsStream(path))
  catch {
    case e: IOException =>
      e.printStackTrace()
  }

  def getSprite(x: Int, y: Int): BufferedImage = sheet.getSubimage(x, y, 16, 16)
}
