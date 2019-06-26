package de.htwg.se.roguelike.aview.gui

import java.awt.image.BufferedImage
import java.awt.{Color, Font, Graphics2D}

//import de.htwg.se.roguelike.aview.tui.State
import de.htwg.se.roguelike.controller.{Controller, GameStatus}
import de.htwg.se.roguelike.model._
import javax.swing.ImageIcon
//import javax.xml.ws.handler.MessageContext.Scope

import scala.swing.event.{ButtonClicked, SelectionChanged}
import scala.swing.{Button, Dimension, FlowPanel, ListView, Panel, ScrollPane}

case class guiInventoryPotion(controller: Controller, gui: SwingGui) extends StateGui {
  override def processInputLine(input: String): Unit = {
    input match {
      case "x" => controller.setGameStatus(GameStatus.INVENTORY)
      case "q" =>
      case _ =>
        input.toList.filter(c => c != ' ').filter(_.isDigit).map(c => c.toString.toInt) match {
          case index :: Nil => controller.usePotion(index)
          case _ =>
        }
    }
  }

  override def handle(): Unit = {
    val e = controller.gameStatus
    e match {
      case GameStatus.INVENTORYPOTION => gui.state = this
      case GameStatus.INVENTORY => gui.state = new guiInventoryMain(controller, gui)
      case _ =>
        print("Wrong GameStatus!!!")
    }
  }

  override def drawPanel(SCALE: Int): Panel = {

    val exitButtonImage = new SpriteSheet("resources/exitButtonIcon.png")
    val exitIcon = new ImageIcon(exitButtonImage.getImage().getScaledInstance(128 * SCALE, 20 * SCALE, java.awt.Image.SCALE_SMOOTH))

    val useButtonImage = new SpriteSheet("resources/usePotionButtonIcon.png")
    val useIcon = new ImageIcon(useButtonImage.getImage().getScaledInstance(128 * SCALE, 20 * SCALE, java.awt.Image.SCALE_SMOOTH))

    val sortButtonImage = new SpriteSheet("resources/sortItemIcons.png")
    val sortVIcon = new ImageIcon(sortButtonImage.getSprite(0,0,20).getScaledInstance(20 * SCALE, 20 * SCALE, java.awt.Image.SCALE_SMOOTH))
    val sortDIcon = new ImageIcon(sortButtonImage.getSprite(20,0,20).getScaledInstance(20 * SCALE, 20 * SCALE, java.awt.Image.SCALE_SMOOTH))

    val panel = new FlowPanel() {
      preferredSize = new Dimension(256 * SCALE, 144 * SCALE)
      peer.setLayout(null)


      var playerItems = new ListView(controller.player.inventory.potions)
      val scrollBar = new ScrollPane(playerItems)
      scrollBar.peer.setBounds(128 * SCALE, 0, 128 * SCALE, 72 * SCALE)

      playerItems.peer.setBounds(128 * SCALE, 0, 128 * SCALE, 72 * SCALE)
      listenTo(playerItems.selection)
      contents += scrollBar

      val use = new Button()
      use.peer.setIcon(useIcon)
      listenTo(use)
      use.peer.setBounds(0 * SCALE, 104 * SCALE, 128 * SCALE, 20 * SCALE)
      contents += use

      val exitButton = new Button()
      exitButton.peer.setIcon(exitIcon)
      listenTo(exitButton)
      exitButton.peer.setBounds(0 * SCALE, 124 * SCALE, 128 * SCALE, 20 * SCALE)
      contents += exitButton

      val sortButtonPower = new Button()
      sortButtonPower.peer.setIcon(sortDIcon)
      listenTo(sortButtonPower)
      sortButtonPower.peer.setBounds(130 * SCALE, 104 * SCALE, 20 * SCALE, 20 * SCALE)
      contents += sortButtonPower

      val sortButtonValue = new Button()
      sortButtonValue.peer.setIcon(sortVIcon)
      listenTo(sortButtonValue)
      sortButtonValue.peer.setBounds(130 * SCALE, 124 * SCALE, 20 * SCALE, 20 * SCALE)
      contents += sortButtonValue

      reactions += {
        case ButtonClicked(e) if e == use => controller.usePotion(playerItems.peer.getSelectedIndex + 1)
        case ButtonClicked(e) if e == exitButton => controller.setGameStatus(GameStatus.INVENTORY)
        case ButtonClicked(b) if b == sortButtonPower => controller.playerSortInventoryPower()
        case ButtonClicked(b) if b == sortButtonValue => controller.playerSortInventoryValue()
        case SelectionChanged(_) => controller.repaint()
      }

      //enemyItems.peer.setDragEnabled(true) maybe iwi bei inventory?!?!??!?!?
      //enemyItems.peer.getDragEnabled maybe iwi bei inventory?!?!??!?!?


      override def paintComponent(g: Graphics2D): Unit = {
        val inventoryBackground = new SpriteSheet("resources/inventoryBackground.png").getImage()
        g.drawImage(inventoryBackground, 0, 0, 256 * SCALE, 144 * SCALE, null)

        //-HealthBar
        g.setFont(new Font("TimesRoman", Font.BOLD, 7 * SCALE))
        g.setColor(Color.BLACK)
        g.fillRect(4 * SCALE, 22 * SCALE, 105 * SCALE, 15 * SCALE)
        g.setColor(Color.RED)

        var HealthHelp1 = 0
        var healthbarWidth = 0
        if (controller.player.maxHealth < 200) {
          HealthHelp1 = controller.player.maxHealth / 100
          val HealthHelp2 = controller.player.health / HealthHelp1
          healthbarWidth = HealthHelp2 * 105 / controller.player.maxHealth
        } else {
          HealthHelp1 = controller.player.maxHealth / 100
          healthbarWidth = controller.player.health * 105 / controller.player.maxHealth
        }

        g.fillRect(4 * SCALE, 22 * SCALE, healthbarWidth * SCALE, 15 * SCALE)
        g.drawRect(4 * SCALE, 22 * SCALE, 105 * SCALE, 15 * SCALE)
        g.setColor(Color.WHITE)
        g.drawString("Health: " + controller.player.health + "/" + controller.player.maxHealth, 30 * SCALE, 32 * SCALE)

        //-ManaBar
        g.setColor(Color.BLACK)
        g.fillRect(4 * SCALE, 40 * SCALE, 105 * SCALE, 15 * SCALE)
        g.setColor(Color.BLUE)

        var ManaHelp1 = 0
        var manabarWidth = 0
        if (controller.player.maxMana < 200) {
          ManaHelp1 = controller.player.maxMana / 100
          val ManaHelp2 = controller.player.mana / ManaHelp1
          manabarWidth = ManaHelp2 * 105 / controller.player.maxMana
        } else {
          ManaHelp1 = controller.player.maxMana / 100
          manabarWidth = controller.player.mana * 105 / controller.player.maxMana
        }

        g.fillRect(4 * SCALE, 40 * SCALE, manabarWidth * SCALE, 15 * SCALE)
        g.drawRect(4 * SCALE, 40 * SCALE, 105 * SCALE, 15 * SCALE)
        g.setColor(Color.WHITE)
        g.drawString("Mana: " + controller.player.mana + "/" + controller.player.maxMana, 30 * SCALE, 50 * SCALE)

        //-Level
        g.setFont(new Font("TimesRoman", Font.BOLD, 10 * SCALE))
        g.drawString("Level: " + controller.player.lvl, 5 * SCALE, 10 * SCALE)
        g.drawString("ATK: " + controller.player.attack, 70 * SCALE, 10 * SCALE)
        g.setFont(new Font("TimesRoman", Font.BOLD, 6 * SCALE))
        g.drawString("Gold: " + controller.player.gulden, 70 * SCALE, 20 * SCALE)
        g.drawString("EXP: " + controller.player.exp + "/" + controller.player.maxExp , 5 * SCALE, 20 * SCALE)

        //-Stats
        g.setFont(new Font("TimesRoman", Font.BOLD, 7 * SCALE))
        g.setColor(Color.WHITE)
        g.drawString("Helmet: " + controller.player.helmet.armor, 5 * SCALE, 80 * SCALE)
        g.drawString("Chest: " + controller.player.chest.armor, 50 * SCALE, 80 * SCALE)
        g.drawString("Pants: " + controller.player.pants.armor, 95 * SCALE, 80 * SCALE)
        g.drawString("Boots: " + controller.player.boots.armor, 5 * SCALE, 95 * SCALE)
        g.drawString("Gloves: " + controller.player.gloves.armor, 50 * SCALE, 95 * SCALE)
        g.drawString("R-Hand: " + controller.player.rightHand.dmg, 95 * SCALE, 90 * SCALE)
        g.drawString("L-Hand: " + controller.player.leftHand.dmg, 95 * SCALE, 100 * SCALE)


        //-PotionIcon
        playerItems.peer.getSelectedValue match {
          case potion: Potion => g.drawImage(getTexture(potion.textureIndex, "resources/PotionTextures.png"), 160 * SCALE, 75 * SCALE, 64 * SCALE, 64 * SCALE, null)
          case _ => {
            g.drawImage(getTexture(24, "resources/PotionTextures.png"), 160 * SCALE, 75 * SCALE, 64 * SCALE, 64 * SCALE, null)
            println("NichtsAusgewählt")
          }
        }
      }

      def getTexture(index: Int, path: String): BufferedImage = {
        val textures = new SpriteSheet(path)
        val x = index % 5
        val y = index / 5
        textures.getSprite(32 * x, 32 * y, 32)
      }
    }
    panel
  }
}
