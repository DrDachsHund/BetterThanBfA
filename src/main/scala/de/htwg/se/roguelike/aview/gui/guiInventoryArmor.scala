package de.htwg.se.roguelike.aview.gui

import java.awt.{Color, Font, Graphics2D}

import de.htwg.se.roguelike.aview.tui.State
import de.htwg.se.roguelike.controller.{Controller, GameStatus}
import de.htwg.se.roguelike.model._
import javax.swing.ImageIcon

import scala.swing.event.ButtonClicked
import scala.swing.{Button, Dimension, FlowPanel, ListView, Panel, ScrollPane}

case class guiInventoryArmor(controller: Controller, gui: SwingGui) extends StateGui {
  override def processInputLine(input: String): Unit = {
    input match {
      case "x" => controller.setGameStatus(GameStatus.INVENTORY)
      case "H" => controller.unEquipHelmet()
      case "C" => controller.unEquipChest()
      case "P" => controller.unEquipPants()
      case "B" => controller.unEquipBoots()
      case "G" => controller.unEquipGloves()
      case "q" =>
      case _ =>
        input.toList.filter(c => c != ' ').filter(_.isDigit).map(c => c.toString.toInt) match {
          case index :: Nil => controller.equipArmor(index)
          case _ =>
        }
    }
  }

  override def handle(): Unit = {
    val e = controller.gameStatus
    e match {
      case GameStatus.INVENTORYARMOR => gui.state = this
      case GameStatus.INVENTORY => gui.state = new guiInventoryMain(controller, gui)
      case _ =>
        print("Wrong GameStatus!!!")
    }
  }

  override def drawPanel(SCALE: Int): Panel = {

    val exitButtonImage = new SpriteSheet("resources/exitButtonIcon.png")
    val exitIcon = new ImageIcon(exitButtonImage.getImage().getScaledInstance(128 * SCALE, 20 * SCALE, java.awt.Image.SCALE_SMOOTH))

    val equipButtonImage = new SpriteSheet("resources/equipButtonIcon.png")
    val equipIcon = new ImageIcon(equipButtonImage.getImage().getScaledInstance(128 * SCALE, 20 * SCALE, java.awt.Image.SCALE_SMOOTH))

    def getImageIcon(item: Item): ImageIcon = {
      item match {
        case armor: Armor => armor match {
          case helm: Helmet => getTexture(helm.textureIndex, "resources/HelmTextures.png")
          case chest: Chest => getTexture(chest.textureIndex, "resources/ChestTextures.png")
          case pants: Pants => getTexture(pants.textureIndex, "resources/PantsTextures.png")
          case boots: Boots => getTexture(boots.textureIndex, "resources/BootsTextures.png")
          case gloves: Gloves => getTexture(gloves.textureIndex, "resources/GlovesTextures.png")
        }
        case weapon: Weapon => getTexture(weapon.textureIndex, "resources/WeaponTextures.png")
        case potion: Potion => getTexture(potion.textureIndex, "resources/PotionTextures.png")
      }
    }

    def getTexture(index: Int, path: String): ImageIcon = {
      val weaponTextures = new SpriteSheet(path)
      val x = index % 5
      val y = index / 5
      new ImageIcon(weaponTextures.getSprite(32 * x, 32 * y, 32).getScaledInstance(30 * SCALE, 30 * SCALE, java.awt.Image.SCALE_SMOOTH))
    }


    val panel = new FlowPanel() {
      preferredSize = new Dimension(256 * SCALE, 144 * SCALE)
      peer.setLayout(null)


      var playerItems = new ListView(controller.player.inventory.armor)
      val scrollBar = new ScrollPane(playerItems)
      scrollBar.peer.setBounds(0, 0, 128 * SCALE, 72 * SCALE)

      playerItems.peer.setBounds(0, 0, 128 * SCALE, 72 * SCALE)
      listenTo(playerItems.selection)
      contents += scrollBar

      val equip = new Button()
      equip.peer.setIcon(equipIcon)
      listenTo(equip)
      equip.peer.setBounds(0 * SCALE, 104 * SCALE, 128 * SCALE, 20 * SCALE)
      contents += equip

      val exitButton = new Button()
      exitButton.peer.setIcon(exitIcon)
      listenTo(exitButton)
      exitButton.peer.setBounds(0 * SCALE, 124 * SCALE, 128 * SCALE, 20 * SCALE)
      contents += exitButton

      //---RightSideCurrentEquipped

      val helmetButton = new Button()
      helmetButton.peer.setIcon(getImageIcon(controller.player.helmet))
      helmetButton.peer.setBounds(177 * SCALE, 5 * SCALE, 30 * SCALE, 30 * SCALE)
      listenTo(helmetButton)
      contents += helmetButton

      val chestButton = new Button()
      chestButton.peer.setIcon(getImageIcon(controller.player.chest))
      chestButton.peer.setBounds(177 * SCALE, 40 * SCALE, 30 * SCALE, 30 * SCALE)
      listenTo(chestButton)
      contents += chestButton

      val pantsButton = new Button()
      pantsButton.peer.setIcon(getImageIcon(controller.player.pants))
      pantsButton.peer.setBounds(177 * SCALE, 75 * SCALE, 30 * SCALE, 30 * SCALE)
      listenTo(pantsButton)
      contents += pantsButton

      val bootsButton = new Button()
      bootsButton.peer.setIcon(getImageIcon(controller.player.boots))
      bootsButton.peer.setBounds(177 * SCALE, 110 * SCALE, 30 * SCALE, 30 * SCALE)
      listenTo(bootsButton)
      contents += bootsButton

      val glovesButton = new Button()
      glovesButton.peer.setIcon(getImageIcon(controller.player.gloves))
      glovesButton.peer.setBounds(137 * SCALE, 5 * SCALE, 30 * SCALE, 30 * SCALE)
      listenTo(glovesButton)
      contents += glovesButton

      val weaponRightHandButton = new Button()
      weaponRightHandButton.peer.setIcon(getImageIcon(controller.player.rightHand))
      weaponRightHandButton.peer.setBounds(217 * SCALE, 40 * SCALE, 30 * SCALE, 30 * SCALE)
      listenTo(weaponRightHandButton)
      contents += weaponRightHandButton

      val weaponLeftHandButton = new Button()
      weaponLeftHandButton.peer.setIcon(getImageIcon(controller.player.leftHand))
      weaponLeftHandButton.peer.setBounds(137 * SCALE, 40 * SCALE, 30 * SCALE, 30 * SCALE)
      listenTo(weaponLeftHandButton)
      contents += weaponLeftHandButton

      val sortButtonPower = new Button()
      //sortButtonPower.peer.setIcon(potionIcon)
      listenTo(sortButtonPower)
      sortButtonPower.peer.setBounds(130 * SCALE, 104 * SCALE, 20 * SCALE, 20 * SCALE)
      contents += sortButtonPower

      val sortButtonValue = new Button()
      //sortButtonValue.peer.setIcon(potionIcon)
      listenTo(sortButtonValue)
      sortButtonValue.peer.setBounds(130 * SCALE, 124 * SCALE, 20 * SCALE, 20 * SCALE)
      contents += sortButtonValue

      reactions += {
        case ButtonClicked(e) if e == equip => controller.equipArmor(playerItems.peer.getSelectedIndex + 1)
        case ButtonClicked(e) if e == exitButton => controller.setGameStatus(GameStatus.INVENTORY)
        case ButtonClicked(b) if b == helmetButton => controller.unEquipHelmet()
        case ButtonClicked(b) if b == chestButton => controller.unEquipChest()
        case ButtonClicked(b) if b == pantsButton => controller.unEquipPants()
        case ButtonClicked(b) if b == bootsButton => controller.unEquipBoots()
        case ButtonClicked(b) if b == glovesButton => controller.unEquipGloves()
        case ButtonClicked(b) if b == weaponRightHandButton => controller.unEquipRightHand()
        case ButtonClicked(b) if b == weaponLeftHandButton => controller.unEquipLeftHand()
        case ButtonClicked(b) if b == sortButtonPower => controller.playerSortInventoryPower()
        case ButtonClicked(b) if b == sortButtonValue => controller.playerSortInventoryValue()

        //case SelectionChanged(_) => controller.repaint()
      }

      //enemyItems.peer.setDragEnabled(true) maybe iwi bei inventory?!?!??!?!?
      //enemyItems.peer.getDragEnabled maybe iwi bei inventory?!?!??!?!?


      override def paintComponent(g: Graphics2D): Unit = {
        val inventoryBackground = new SpriteSheet("resources/inventoryBackground.png").getImage()
        g.drawImage(inventoryBackground, 0, 0, 256 * SCALE, 144 * SCALE, null)

        g.setFont(new Font("TimesRoman", Font.BOLD, 7 * SCALE))
        g.setColor(Color.WHITE)
        g.drawString("Helmet: " + controller.player.helmet.armor, 5 * SCALE, 80 * SCALE)
        g.drawString("Chest: " + controller.player.chest.armor, 50 * SCALE, 80 * SCALE)
        g.drawString("Pants: " + controller.player.pants.armor, 95 * SCALE, 80 * SCALE)
        g.drawString("Boots: " + controller.player.boots.armor, 5 * SCALE, 95 * SCALE)
        g.drawString("Gloves: " + controller.player.gloves.armor, 50 * SCALE, 95 * SCALE)
        g.drawString("R-Hand: " + controller.player.rightHand.dmg, 95 * SCALE, 90 * SCALE)
        g.drawString("L-Hand: " + controller.player.leftHand.dmg, 95 * SCALE, 100 * SCALE)
      }
    }
    panel
  }
}
