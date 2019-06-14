package de.htwg.se.roguelike.aview.gui

import java.awt.Graphics2D
import java.awt.image.BufferedImage

import de.htwg.se.roguelike.aview.tui.State
import de.htwg.se.roguelike.controller.{Controller, GameStatus}
import de.htwg.se.roguelike.model._
import javax.swing.{BoxLayout, ListSelectionModel}

import scala.swing.event.ButtonClicked
import scala.swing.{BorderPanel, BoxPanel, Button, Component, Dimension, FlowPanel, ListView, Orientation, Panel, ScrollBar, ScrollPane}

case class guiLootEnemy(controller: Controller, gui: SwingGui) extends StateGui {
  override def processInputLine(input: String): Unit = {
    input match {
      case "q" =>
      case "x" => controller.setGameStatus(GameStatus.LEVEL)
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
      case GameStatus.LEVEL => gui.state = new guiMain(controller, gui)
      case GameStatus.LOOTENEMY => gui.state = this
      case _ =>
        print("Wrong GameStatus!!!")
    }
  }

  override def drawPanel(SCALE: Int): Panel = {

    var enemyItems = new ListView(controller.enemyLoot)

    val panel = new FlowPanel() {
      preferredSize = new Dimension(256 * SCALE, 144 * SCALE + 20)
      peer.setLayout(null)



      enemyItems.peer.setBounds(0, 0, 128 * SCALE, 144 * SCALE)
      contents += enemyItems

      val lootButton = new Button("Loot")
      listenTo(lootButton)
      lootButton.peer.setBounds(128 * SCALE, 124 * SCALE, 100 * SCALE, 20 * SCALE)
      contents += lootButton


      reactions += {
        case ButtonClicked(lb) if lb == lootButton => {
          controller.lootingEnemy(enemyItems.peer.getSelectedIndex + 1)
        }
      }

      override def paint(g: Graphics2D): Unit = {

        val backgroundSpriteSheet = new SpriteSheet("16bitSpritesBackground.png")
        val errorTexture = backgroundSpriteSheet.getSprite(32,16)

        enemyItems.peer.getSelectedValue match {
          case armor:Armor => {
            case helm: Helmet =>
            case chest: Chest =>
            case pants: Pants =>
            case boots: Boots =>
            case gloves: Gloves =>
          }
          case weapon:Weapon =>
          case potion:Potion => {
            case healPotion:HealPotion =>
            case manaPotion:ManaPotion =>
        }
        }

      }
/*
      def getHelmTexture(index:Int): BufferedImage = {
        val helmTexture = new SpriteSheet("HelmTextures.png")
      }
      */
    }
    panel
  }
}
