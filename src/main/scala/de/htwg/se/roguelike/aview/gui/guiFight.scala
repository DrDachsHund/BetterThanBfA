package de.htwg.se.roguelike.aview.gui

import java.awt.{Color, Font, Graphics2D}
import java.awt.image.BufferedImage

import de.htwg.se.roguelike.controller.{Controller, GameStatus}

import scala.swing.{Dimension, Label, Panel}

case class guiFight(controller: Controller, gui: SwingGui) extends StateGui {
  override def processInputLine(input: String): Unit = {
    input match {
      case "q" =>
      case "1" => controller.attack()
      case "2" => controller.block()
      case "3" => controller.special()
      case "r" => controller.run()
      //case "enter" match
      case "i" =>
        controller.setGameStatus(GameStatus.INVENTORY)
      //tui.inventoryGameStatus = GameStatus.FIGHT
      case _ =>
        print("Wrong Input!!!")
    }
  }

  override def handle(): Unit = {
    val e = controller.gameStatus
    e match {
      case GameStatus.LEVEL => gui.state = new guiMain(controller, gui)
      case GameStatus.FIGHT => gui.state = this
      case GameStatus.FIGHTSTATUS => gui.state = this
      case GameStatus.INVENTORY => gui.state = new guiInventoryMain(controller, gui)
      case GameStatus.GAMEOVER => gui.state = new guiGameOver(controller, gui)
      case GameStatus.PLAYERLEVELUP => gui.state = new guiPlayerLevelUp(controller, gui)
      case GameStatus.LOOTENEMY => gui.state = new guiLootEnemy(controller, gui)
      case _ =>
        print("Wrong GameStatus!!!")
    }
  }

  override def drawPanel(SCALE: Int): Panel = {

    val panel = new Panel {

      //ersma so aber eig eigene texturen für fihgt
      val playerSpriteSheet = new SpriteSheet("Player.png")
      val fightSpriteSheet = new SpriteSheet("TextBar.png")
      val fightBackgroundSpriteSheet = new SpriteSheet("FightBackground1.png")
      val enemiesSpriteSheet = new SpriteSheet("Enemy.png")
      val backgroundSpriteSheet = new SpriteSheet("16bitSpritesBackground.png")

      val playerTexture = playerSpriteSheet.getSprite(16, 0)
      val enemyTextureBlue = enemiesSpriteSheet.horizontalFlip(enemiesSpriteSheet.getSprite(0, 32)) //zum flippen vll in eigene klasse?!?!?!?
      val enemyTextureRed = enemiesSpriteSheet.horizontalFlip(enemiesSpriteSheet.getSprite(0, 16))
      val enemyTextureGreen = enemiesSpriteSheet.horizontalFlip(enemiesSpriteSheet.getSprite(0, 0))
      val errorTexture = backgroundSpriteSheet.getSprite(32, 16)
      val fight = fightSpriteSheet.sheet
      val fightBackground = fightBackgroundSpriteSheet.sheet

      preferredSize = new Dimension(256 * SCALE, 144 * SCALE + 20)

      override def paint(g: Graphics2D): Unit = {

        g.drawImage(fightBackground, 0, 0, 256 * SCALE, 144 * SCALE, null)
        g.drawImage(playerTexture, 10 * SCALE, 60 * SCALE, 32 * SCALE, 32 * SCALE, null)

        controller.currentEnemy.enemyType match {
          case 1 => g.drawImage(enemyTextureBlue, 210 * SCALE, 60 * SCALE, 32 * SCALE, 32 * SCALE, null)
          case 2 => g.drawImage(enemyTextureRed, 210 * SCALE, 60 * SCALE, 32 * SCALE, 32 * SCALE, null)
          case 3 => g.drawImage(enemyTextureGreen, 210 * SCALE, 60 * SCALE, 32 * SCALE, 32 * SCALE, null)
          case _ => g.drawImage(errorTexture, 210 * SCALE, 60 * SCALE, 32 * SCALE, 32 * SCALE, null)
        }


        g.drawImage(fight, 0, 0, 256 * SCALE, 144 * SCALE, null)

        //HealthBar
        g.setColor(Color.BLACK)
        g.fillRect(4 * SCALE, 22 * SCALE, 25 * SCALE, 5 * SCALE)
        g.setColor(Color.RED)
        val HealthHelp1 = controller.player.maxHealth / 100
        val HealthHelp2 = controller.player.health / HealthHelp1
        val healthbarWidth = HealthHelp2 * 25 / 100
        g.fillRect(4 * SCALE, 22 * SCALE, healthbarWidth * SCALE, 5 * SCALE)
        g.setColor(Color.BLACK)
        g.drawRect(4 * SCALE, 22 * SCALE, 25 * SCALE, 5 * SCALE)
        g.drawString("Health", 4 * SCALE, 21 * SCALE)
        g.setColor(Color.WHITE)
        g.drawString(controller.player.health + "/" + controller.player.maxHealth, 8 * SCALE, 26 * SCALE)
        //g.drawString(help2 + "%", 8 * SCALE, 24 * SCALE) für prozentAnzeige

        g.setColor(Color.BLACK)
        g.fillRect(226 * SCALE, 22 * SCALE, 25 * SCALE, 5 * SCALE)
        g.setColor(Color.RED)
        val EHealthHelp1 = 100 / 100 //ersma so da enemy kein maxHealth
        val EHealthHelp2 = controller.currentEnemy.health / EHealthHelp1
        val EhealthbarWidth = EHealthHelp2 * 25 / 100
        g.fillRect(226 * SCALE, 22 * SCALE, EhealthbarWidth * SCALE, 5 * SCALE)
        g.setColor(Color.BLACK)
        g.drawRect(226 * SCALE, 22 * SCALE, 25 * SCALE, 5 * SCALE)
        g.drawString("Health", 226 * SCALE, 21 * SCALE)
        g.setColor(Color.WHITE)
        g.drawString(controller.currentEnemy.health + "/" + 100, 230 * SCALE, 26 * SCALE)

        //ManaBar
        g.setColor(Color.BLACK)
        g.fillRect(4 * SCALE, 35 * SCALE, 25 * SCALE, 5 * SCALE)
        g.setColor(Color.BLUE)
        val ManaHelp1 = controller.player.maxMana / 100
        val ManaHelp2 = controller.player.mana / ManaHelp1
        val manabarWidth = ManaHelp2 * 25 / 100
        g.fillRect(4 * SCALE, 35 * SCALE, manabarWidth * SCALE, 5 * SCALE)
        g.setColor(Color.BLACK)
        g.drawRect(4 * SCALE, 35 * SCALE, 25 * SCALE, 5 * SCALE)
        g.drawString("Mana", 4 * SCALE, 34 * SCALE)
        g.setColor(Color.WHITE)
        g.drawString(controller.player.mana + "/" + controller.player.maxMana, 8 * SCALE, 39 * SCALE)

        g.setColor(Color.BLACK)
        g.fillRect(226 * SCALE, 35 * SCALE, 25 * SCALE, 5 * SCALE)
        g.setColor(Color.BLUE)
        val EManaHelp1 = 100 / 100 //enemy maxMana fehltnoch
        val EManaHelp2 = controller.currentEnemy.mana / EManaHelp1
        val EmanabarWidth = EManaHelp2 * 25 / 100
        g.fillRect(226 * SCALE, 35 * SCALE, EmanabarWidth * SCALE, 5 * SCALE)
        g.setColor(Color.BLACK)
        g.drawRect(226 * SCALE, 35 * SCALE, 25 * SCALE, 5 * SCALE)
        g.drawString("Mana", 226 * SCALE, 34 * SCALE)
        g.setColor(Color.WHITE)
        g.drawString(controller.currentEnemy.mana + "/" + 100, 230 * SCALE, 39 * SCALE)

        //--Name--LEVEL--
        g.setColor(Color.BLACK)
        g.setFont(new Font("TimesRoman", Font.BOLD, 5 * SCALE))
        g.drawString(controller.player.name, 5 * SCALE, 10 * SCALE)
        g.drawString("Level:  " + controller.player.lvl, 5 * SCALE, 15 * SCALE)

        g.setFont(new Font("TimesRoman", Font.PLAIN, 10 * SCALE))
        //g.drawString("Health", 5 * SCALE, 10 * SCALE)
        g.drawString("[1]Attack   [2]:Block   [3]:Special   [i]Inventory    [r]:Run", 5 * SCALE, 125 * SCALE)

      }

      repaint()
    }
    panel
  }

}
