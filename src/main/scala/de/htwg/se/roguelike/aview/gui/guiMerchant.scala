package de.htwg.se.roguelike.aview.gui

import java.awt.{Color, Font, Graphics2D}
import java.awt.image.BufferedImage

import de.htwg.se.roguelike.controller.{ControllerInterface, GameStatus}
import de.htwg.se.roguelike.controller.controllerBaseImpl.Controller
import de.htwg.se.roguelike.model._
import de.htwg.se.roguelike.model.levelComponent.{ArmorInterface, PotionInterface, WeaponInterface}
import de.htwg.se.roguelike.model.levelComponent.levelBaseImpl._
import javax.swing.ImageIcon

import scala.swing.event.{ButtonClicked, MouseClicked, SelectionChanged}
import scala.swing.{Button, Dimension, FlowPanel, ListView, Panel, ScrollPane}


case class guiMerchant(controller: ControllerInterface, gui: SwingGui) extends StateGui {
  override def processInputLine(input: String): Unit = {
    input match {
      case "q" =>
      case "x" => controller.run()
      case _ =>
        input.toList.filter(c => c != ' ').filter(_.isDigit).map(c => c.toString.toInt) match {
          case index :: Nil => controller.lootingEnemy(index)
          case _ =>
        }
    }
  }

  override def handle(): Unit = {
    val e = controller.gameStatus
    e match {
      case GameStatus.LEVEL => gui.state = new guiLevel(controller, gui)
      case GameStatus.MERCHANT => gui.state = this
      case _ =>
        print("Wrong GameStatus!!!")
    }
  }

  override def drawPanel(SCALE: Int): Panel = {

    val exitButtonImage = new SpriteSheet("resources/exitButtonIcon.png")
    val exitIcon = new ImageIcon(exitButtonImage.getImage().getScaledInstance(128 * SCALE, 24 * SCALE, java.awt.Image.SCALE_SMOOTH))

    val buyButtonImage = new SpriteSheet("resources/buyButtonIcon.png")
    val buyIcon = new ImageIcon(buyButtonImage.getImage().getScaledInstance(128 * SCALE, 24 * SCALE, java.awt.Image.SCALE_SMOOTH))

    val sellButtonImage = new SpriteSheet("resources/sellButtonIcon.png")
    val sellIcon = new ImageIcon(sellButtonImage.getImage().getScaledInstance(128 * SCALE, 24 * SCALE, java.awt.Image.SCALE_SMOOTH))

    val restockButtonImage = new SpriteSheet("resources/restockButtonIcon.png")
    val restockIcon = new ImageIcon(restockButtonImage.getImage().getScaledInstance(128 * SCALE, 24 * SCALE, java.awt.Image.SCALE_SMOOTH))

    val sortButtonImage = new SpriteSheet("resources/sortItemIcons.png")
    val sortVIcon = new ImageIcon(sortButtonImage.getSprite(0,0,20).getScaledInstance(24 * SCALE, 24 * SCALE, java.awt.Image.SCALE_SMOOTH))
    val sortDIcon = new ImageIcon(sortButtonImage.getSprite(20,0,20).getScaledInstance(24 * SCALE, 24 * SCALE, java.awt.Image.SCALE_SMOOTH))

    val panel = new FlowPanel() {

      preferredSize = new Dimension(256 * SCALE, 144 * SCALE)
      peer.setLayout(null)

      var playerItems = new ListView(controller.inventoryAsOneVector())
      val scrollBar = new ScrollPane(playerItems)
      scrollBar.peer.setBounds(0 * SCALE, 0, 128 * SCALE, 72 * SCALE)

      playerItems.peer.setBounds(0 * SCALE, 0 * SCALE, 128 * SCALE, 72 * SCALE)
      listenTo(playerItems.selection)
      contents += scrollBar

      var merchantItems = new ListView(controller.merchant.inventory)
      val merchantScrollBar = new ScrollPane(merchantItems)
      merchantScrollBar.peer.setBounds(128 * SCALE, 0, 128 * SCALE, 72 * SCALE)

      merchantItems.peer.setBounds(128 * SCALE, 0 * SCALE, 128 * SCALE, 72 * SCALE)
      listenTo(merchantItems.selection)
      contents += merchantScrollBar

      val sell = new Button()
      sell.peer.setBounds(0 * SCALE, 96 * SCALE, 128 * SCALE, 24 * SCALE)
      sell.peer.setIcon(sellIcon)
      listenTo(sell)
      contents += sell

      val exit = new Button()
      exit.peer.setBounds(0 * SCALE, 120 * SCALE, 128 * SCALE, 24 * SCALE)
      exit.peer.setIcon(exitIcon)
      listenTo(exit)
      contents += exit

      val buy = new Button()
      buy.peer.setBounds(128 * SCALE, 96 * SCALE, 128 * SCALE, 24 * SCALE)
      buy.peer.setIcon(buyIcon)
      listenTo(buy)
      contents += buy

      val restock = new Button()
      restock.peer.setBounds(128 * SCALE, 120 * SCALE, 128 * SCALE, 24 * SCALE)
      restock.peer.setIcon(restockIcon)
      listenTo(restock)
      contents += restock

      val sortButtonPower = new Button()
      sortButtonPower.peer.setIcon(sortDIcon)
      listenTo(sortButtonPower)
      sortButtonPower.peer.setBounds(80 * SCALE, 72 * SCALE, 24 * SCALE, 24 * SCALE)
      contents += sortButtonPower

      val sortButtonValue = new Button()
      sortButtonValue.peer.setIcon(sortVIcon)
      listenTo(sortButtonValue)
      sortButtonValue.peer.setBounds(56 * SCALE, 72 * SCALE, 24 * SCALE, 24 * SCALE)
      contents += sortButtonValue

      reactions += {
        case ButtonClicked(s) if s == sell => controller.sellItem(playerItems.peer.getSelectedIndex)
        case ButtonClicked(e) if e == exit => controller.run()
        case ButtonClicked(b) if b == buy => controller.buyItem(merchantItems.peer.getSelectedIndex)
        case ButtonClicked(b) if b == sortButtonPower => controller.playerSortInventoryPower()
        case ButtonClicked(b) if b == sortButtonValue => controller.playerSortInventoryValue()
        case ButtonClicked(rs) if rs == restock => controller.restock

        case SelectionChanged(_) => controller.repaint()
      }

      override def paintComponent(g: Graphics2D): Unit = {

        val inventoryBackground = new SpriteSheet("resources/inventoryBackground.png").getImage()
        g.drawImage(inventoryBackground, 0, 0, 256 * SCALE, 144 * SCALE, null)

        g.setFont(new Font("TimesRoman", Font.BOLD, 7 * SCALE))
        g.setColor(Color.WHITE)
        g.drawString(controller.player.name, 0 * SCALE, 82 * SCALE)
        g.drawString("Gulden: " + controller.player.gulden, 0 * SCALE, 92 * SCALE)
        g.drawString("Merchant", 168 * SCALE, 82 * SCALE)
        g.drawString("Gulden: " + controller.merchant.gulden, 168 * SCALE, 92 * SCALE)


        playerItems.peer.getSelectedValue match {
          case armor: ArmorInterface => armor match {
            case helm: Helmet => g.drawImage(getTexture(helm.textureIndex, "resources/HelmTextures.png"), 104 * SCALE, 72 * SCALE, 24 * SCALE, 24 * SCALE, null)
            case chest: Chest => g.drawImage(getTexture(chest.textureIndex, "resources/ChestTextures.png"), 104 * SCALE, 72 * SCALE, 24 * SCALE, 24 * SCALE, null)
            case pants: Pants => g.drawImage(getTexture(pants.textureIndex, "resources/PantsTextures.png"), 104 * SCALE, 72 * SCALE, 24 * SCALE, 24 * SCALE, null)
            case boots: Boots => g.drawImage(getTexture(boots.textureIndex, "resources/BootsTextures.png"), 104 * SCALE, 72 * SCALE, 24 * SCALE, 24 * SCALE, null)
            case gloves: Gloves => g.drawImage(getTexture(gloves.textureIndex, "resources/GlovesTextures.png"), 104 * SCALE, 72 * SCALE, 24 * SCALE, 24 * SCALE, null)
          }
          case weapon: WeaponInterface => g.drawImage(getTexture(weapon.textureIndex, "resources/WeaponTextures.png"), 104 * SCALE, 72 * SCALE, 24 * SCALE, 24 * SCALE, null)
          case potion: PotionInterface => g.drawImage(getTexture(potion.textureIndex, "resources/PotionTextures.png"), 104 * SCALE, 72 * SCALE, 24 * SCALE, 24 * SCALE, null)
          case _ => {
            println("NichtsAusgewählt")
          }
        }

        merchantItems.peer.getSelectedValue match {
          case armor: ArmorInterface => armor match {
            case helm: Helmet => g.drawImage(getTexture(helm.textureIndex, "resources/HelmTextures.png"), 128 * SCALE, 72 * SCALE, 24 * SCALE, 24 * SCALE, null)
            case chest: Chest => g.drawImage(getTexture(chest.textureIndex, "resources/ChestTextures.png"), 128 * SCALE, 72 * SCALE, 24 * SCALE, 24 * SCALE, null)
            case pants: Pants => g.drawImage(getTexture(pants.textureIndex, "resources/PantsTextures.png"), 128 * SCALE, 72 * SCALE, 24 * SCALE, 24 * SCALE, null)
            case boots: Boots => g.drawImage(getTexture(boots.textureIndex, "resources/BootsTextures.png"), 128 * SCALE, 72 * SCALE, 24 * SCALE, 24 * SCALE, null)
            case gloves: Gloves => g.drawImage(getTexture(gloves.textureIndex, "resources/GlovesTextures.png"), 128 * SCALE, 72 * SCALE, 24 * SCALE, 24 * SCALE, null)
          }
          case weapon: WeaponInterface => g.drawImage(getTexture(weapon.textureIndex, "resources/WeaponTextures.png"), 128 * SCALE, 72 * SCALE, 24 * SCALE, 24 * SCALE, null)
          case potion: PotionInterface => g.drawImage(getTexture(potion.textureIndex, "resources/PotionTextures.png"), 128 * SCALE, 72 * SCALE, 24 * SCALE, 24 * SCALE, null)
          case _ => {
            println("NichtsAusgewählt")
          }
        }
      }

      def getTexture(index: Int, path: String): BufferedImage = {
        val weaponTextures = new SpriteSheet(path)
        val x = index % 5
        val y = index / 5
        weaponTextures.getSprite(32 * x, 32 * y, 32)
      }
    }
    panel
  }
}
