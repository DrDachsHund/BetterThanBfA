package de.htwg.se.roguelike.aview.gui

import java.awt.Graphics2D
import java.awt.image.BufferedImage

import de.htwg.se.roguelike.aview.State
import de.htwg.se.roguelike.controller.{Controller, GameStatus}
import de.htwg.se.roguelike.model.Tile

import scala.swing.{Dimension, Panel}

case class guiMain(controller: Controller, gui: SwingGui) extends StateGui {

  override def processInputLine(input: String): Unit = {
    input match {
      case "w" => controller.moveUp()
      case "a" => controller.moveLeft()
      case "s" => controller.moveDown()
      case "d" => controller.moveRight()
      case "z" => controller.undo()
      case "y" => controller.redo()
      case _ => println("FEHLER IN GUI")
    }
  }

  override def handle(): Unit = {
    val e = controller.gameStatus
    e match {
      case GameStatus.LEVEL => gui.state = this
      case GameStatus.FIGHT => gui.state = new guiFight(controller, gui)
      case GameStatus.INVENTORY => gui.state = new guiInventoryMain(controller, gui)
      case _ =>
        print("Wrong GameStatus!!!")
    }
  }

  override def drawPanel(SCALE: Int): Panel = {
    //val img = ImageIO.read(getClass.getResource("Test.png"))
    val panel = new Panel {
      val backgroundSpriteSheet = new SpriteSheet("16bitSpritesBackground.png")
      val playerSpriteSheet = new SpriteSheet("Player.png")
      val enemiesSpriteSheet = new SpriteSheet("Enemy.png")

      val levelTextureFlower = backgroundSpriteSheet.getSprite(16, 0)
      val levelTextureGrass = backgroundSpriteSheet.getSprite(0, 0)
      val levelTexturePortal = backgroundSpriteSheet.getSprite(0, 16)
      val playerTexture = playerSpriteSheet.getSprite(16, 0)
      val enemyTextureBlue = enemiesSpriteSheet.getSprite(0, 32)
      val enemyTextureRed = enemiesSpriteSheet.getSprite(0, 16)
      val enemyTextureGreen = enemiesSpriteSheet.getSprite(0, 0)

      val canvas = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB)
      val g = canvas.createGraphics()
      preferredSize = new Dimension(256 * SCALE, 144 * SCALE + 20)

      override def paint(g: Graphics2D): Unit = {

        //---LEVEL
        for (x <- 0 until controller.level.map.sizeX) {
          for (y <- 0 until controller.level.map.sizeY) {
            if (controller.level.map.tile(x, y) == Tile(0)) {
              g.drawImage(levelTextureGrass, y * 16 * SCALE, x * 16 * SCALE, 16 * SCALE, 16 * SCALE, null)
            } else if (controller.level.map.tile(x, y) == Tile(2)) {
              g.drawImage(levelTextureFlower, y * 16 * SCALE, x * 16 * SCALE, 16 * SCALE, 16 * SCALE, null)
            } else {
              g.drawImage(levelTextureGrass, y * 16 * SCALE, x * 16 * SCALE, 16 * SCALE, 16 * SCALE, null)
            }
          }
        }

        //--PLAYER
        g.drawImage(playerTexture, controller.player.posX * 16 * SCALE, controller.player.posY * 16 * SCALE, 16 * SCALE, 16 * SCALE, null)
        //Variable bei Player um Postition zu checken

        //--Enemies
        for (x <- controller.enemies) {
          g.drawImage(enemyTextureBlue, x.posX * 16 * SCALE, x.posY * 16 * SCALE, 12 * SCALE, 12 * SCALE, null)
        }

        //--Portal
        g.drawImage(levelTexturePortal, controller.portal.portalX * 16 * SCALE, controller.portal.portalY * 16 * SCALE, 16 * SCALE, 16 * SCALE, null)

      }

      repaint()
    }
    panel
  }

}
