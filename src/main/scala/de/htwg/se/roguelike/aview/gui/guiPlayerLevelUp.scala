package de.htwg.se.roguelike.aview.gui

import java.awt.{Color, Font, Graphics2D}

import de.htwg.se.roguelike.aview.tui.State
import de.htwg.se.roguelike.controller.{Controller, GameStatus}
import javax.swing.ImageIcon

import scala.swing.event.ButtonClicked
import scala.swing.{Button, Dimension, FlowPanel, Panel}

case class guiPlayerLevelUp(controller: Controller, gui: SwingGui) extends StateGui {
  override def processInputLine(input: String): Unit = {
    input match {
      case "q" =>
      case "1" => controller.lvlUpHealth()
      case "2" => controller.lvlUpMana()
      case "3" => controller.lvlUpAttack()
      case _ =>
        print("Wrong Input!!!")
    }
  }

  override def handle(): Unit = {
    val e = controller.gameStatus
    e match {
      case GameStatus.LOOTENEMY => gui.state = new guiLootEnemy(controller, gui)
      case GameStatus.PLAYERLEVELUP => gui.state = this
      case _ =>
        print("Wrong GameStatus!!!")
    }
  }

  override def drawPanel(SCALE: Int): Panel = {

    val healthButtonImage = new SpriteSheet("resources/healthLevelUpButtonIcon.png")
    val healthIcon = new ImageIcon(healthButtonImage.getImage().getScaledInstance(128 * SCALE, 20 * SCALE, java.awt.Image.SCALE_SMOOTH))

    val attackButtonImage = new SpriteSheet("resources/attackLevelUpButtonIcon.png")
    val attackIcon = new ImageIcon(attackButtonImage.getImage().getScaledInstance(128 * SCALE, 20 * SCALE, java.awt.Image.SCALE_SMOOTH))

    val manaButtonImage = new SpriteSheet("resources/manaLevelUpButtonIcon.png")
    val manaIcon = new ImageIcon(manaButtonImage.getImage().getScaledInstance(128 * SCALE, 20 * SCALE, java.awt.Image.SCALE_SMOOTH))


    val panel = new FlowPanel() {
      preferredSize = new Dimension(256 * SCALE, 144 * SCALE)
      peer.setLayout(null)

      val health = new Button()
      health.peer.setIcon(healthIcon)
      listenTo(health)
      health.peer.setBounds(64 * SCALE, 34 * SCALE, 128 * SCALE, 20 * SCALE)
      contents += health

      val mana = new Button()
      mana.peer.setIcon(manaIcon)
      listenTo(mana)
      mana.peer.setBounds(64 * SCALE, 64 * SCALE, 128 * SCALE, 20 * SCALE)
      contents += mana

      val attack = new Button()
      attack.peer.setIcon(attackIcon)
      listenTo(attack)
      attack.peer.setBounds(64 * SCALE, 94 * SCALE, 128 * SCALE, 20 * SCALE)
      contents += attack

      reactions += {
        case ButtonClicked(h) if h == health => controller.lvlUpHealth()
        case ButtonClicked(m) if m == mana => controller.lvlUpMana()
        case ButtonClicked(a) if a == attack => controller.lvlUpAttack()
      }

      override def paintComponent(g: Graphics2D): Unit = {
        val inventoryBackground = new SpriteSheet("resources/inventoryBackground.png").getImage()

        g.drawImage(inventoryBackground, 0, 0, 256 * SCALE, 144 * SCALE, null)

        //-HealthBar
        g.setFont(new Font("TimesRoman", Font.BOLD, 7 * SCALE))
        g.setColor(Color.BLACK)
        g.fillRect(64 * SCALE, 9 * SCALE, 128 * SCALE, 15 * SCALE)
        g.setColor(Color.RED)

        var HealthHelp1 = 0
        var healthbarWidth = 0
        if (controller.player.maxHealth < 200) {
          HealthHelp1 = controller.player.maxHealth / 100
          val HealthHelp2 = controller.player.health / HealthHelp1
          healthbarWidth = HealthHelp2 * 128 / controller.player.maxHealth
        } else {
          HealthHelp1 = controller.player.maxHealth / 100
          healthbarWidth = controller.player.health * 128 / controller.player.maxHealth
        }

        g.fillRect(64 * SCALE, 9 * SCALE, healthbarWidth * SCALE, 15 * SCALE)
        g.drawRect(64 * SCALE, 9 * SCALE, 128 * SCALE, 15 * SCALE)
        g.setColor(Color.WHITE)
        g.drawString("Health: " + controller.player.health + "/" + controller.player.maxHealth, 96 * SCALE, 19 * SCALE)

        //-ManaBar
        g.setColor(Color.BLACK)
        g.fillRect(64 * SCALE, 124 * SCALE, 128 * SCALE, 15 * SCALE)
        g.setColor(Color.BLUE)
        var ManaHelp1 = 0
        var manabarWidth = 0
        if (controller.player.maxMana < 200) {
          ManaHelp1 = controller.player.maxMana / 100
          val ManaHelp2 = controller.player.mana / ManaHelp1
          manabarWidth = ManaHelp2 * 128 / controller.player.maxMana
        } else {
          ManaHelp1 = controller.player.maxMana / 100
          manabarWidth = controller.player.mana * 128 / controller.player.maxMana
        }
        g.fillRect(64 * SCALE, 124 * SCALE, manabarWidth * SCALE, 15 * SCALE)
        g.drawRect(64 * SCALE, 124 * SCALE, 128 * SCALE, 15 * SCALE)
        g.setColor(Color.WHITE)
        g.drawString("Mana: " + controller.player.mana + "/" + controller.player.maxMana, 96 * SCALE, 134 * SCALE)

        g.drawString("LVL: " + controller.player.lvl, 5 * SCALE, 20 * SCALE)
        g.drawString("EXP: " + controller.player.exp + "/" + controller.player.maxExp, 5 * SCALE, 30 * SCALE)
      }
    }
    panel
  }
}
