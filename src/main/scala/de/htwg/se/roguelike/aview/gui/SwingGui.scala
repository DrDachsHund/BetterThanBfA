package de.htwg.se.roguelike.aview.gui

import java.awt.event.{KeyEvent, KeyListener}
import java.awt.image.BufferedImage
import java.awt.{BorderLayout, Canvas, Graphics2D}
import java.io.IOException

import de.htwg.se.roguelike.controller._
import javax.imageio.ImageIO
import javax.swing.ImageIcon

import scala.swing.Swing.LineBorder
import scala.swing._
import scala.swing.event.Key

class SwingGui(controller: Controller) extends Reactor {

  listenTo(controller)

  //---GUI--


  val frame = new MainFrame()
  val SCALE = 4 // eig in controller but for testing here

  frame.title = "Pog Game"
  setSize

  //frame.peer.requestFocus() //noch testen

  frame.contents = new BorderPanel {
    add(new GPanelLevel(controller, SCALE), BorderPanel.Position.Center)
  }

  frame.menuBar = new GMenueBar(controller)

  //--GUI--

  //frame.peer.setResizable(false)
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

  def setSize(): Unit = {
    val width = 256 * SCALE
    val height = 144 * SCALE + 20
    frame.peer.setSize(new Dimension(width, height))
  }


  frame.peer.addKeyListener(new KeyListener() {
    def keyPressed(e: KeyEvent) {

      if (controller.gameStatus == GameStatus.LEVEL) {
        e.getKeyChar match {
          case 'w' => controller.moveUp()
          case 'a' => controller.moveLeft()
          case 's' => controller.moveDown()
          case 'd' => controller.moveRight()
          case 'r' => controller.createRandomLevel()
          case _ => println("FEHLER IN GUI")
        }
      } else if (controller.gameStatus == GameStatus.FIGHT) {
        e.getKeyChar match {
          case '1' => controller.attack()
          case '2' => controller.block()
          case '3' => controller.special()
          case '4' => controller.run()
          case _ => println("FEHLER IN GUI")
      }}else if (controller.gameStatus == GameStatus.GAMEOVER) {
        e.getKeyChar match {
          case 'n' => controller.newGame()
          case _ => println("FEHLER IN GUI")
        }
      }else if (controller.gameStatus == GameStatus.LOOTENEMY) {
        e.getKeyChar match {
          case 'x' => controller.setGameStatus(GameStatus.LEVEL)
          case _ => println("FEHLER IN GUI")
        }
      }
    }

    def keyReleased(e: KeyEvent) {
      //println("key released")
    }

    def keyTyped(e: KeyEvent) {
      //println("key typed")
    }
  })


}


private class GMenueBar(controller: Controller) extends MenuBar {
  this.peer.setSize(new Dimension(0,20))
  contents += new Menu("File") {
    mnemonic = Key.F //Dose not work
    contents += new MenuItem(Action("New") {
      controller.createLevel()
    })
    mnemonic = Key.R
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
  val playerSpriteSheet = new SpriteSheet("Player.png")
  val enemiesSpriteSheet = new SpriteSheet("Enemy.png")

  val levelTexture = backgroundSpriteSheet.getSprite(16, 0)
  val playerTexture = playerSpriteSheet.getSprite(16, 0)
  val enemiesTexture = enemiesSpriteSheet.getSprite(0,32)

  val canvas = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB)

  val g = canvas.createGraphics()

  preferredSize = new Dimension(256 * SCALE, 144 * SCALE + 20)

  override def paint(g: Graphics2D): Unit = {

    //---LEVEL
    for (x <- 0 until controller.level.map.sizeX) {// hier noch random background
      for (y <- 0 until controller.level.map.sizeY) {
        g.drawImage(levelTexture,y * 16 * SCALE,x * 16 * SCALE, 16 * SCALE, 16 * SCALE, null)
      }
    }

    //--PLAYER
    g.drawImage(playerTexture,controller.player.posX * 16 * SCALE,controller.player.posY *  16 * SCALE, 16 * SCALE, 16 * SCALE, null)

    for (x <- controller.enemies) { //hier noch random color enemies
      g.drawImage(enemiesTexture,x.posX * 16 * SCALE,x.posY *  16 * SCALE, 12 * SCALE, 12 * SCALE, null)
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
