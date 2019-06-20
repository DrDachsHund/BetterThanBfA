package de.htwg.se.roguelike.aview.gui

import de.htwg.se.roguelike.controller.{Controller, GameStatus}

import scala.swing.{FlowPanel, ListView, Panel, ScrollPane}


case class guiMerchant(controller: Controller, gui: SwingGui) extends StateGui {
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

    val panel = new FlowPanel() {

      var playerItems = new ListView(controller.inventoryAsOneVector())
      val scrollBar = new ScrollPane(playerItems)
      scrollBar.peer.setBounds(128 * SCALE, 0, 128 * SCALE, 72 * SCALE)

      playerItems.peer.setBounds(128 * SCALE, 0, 128 * SCALE, 72 * SCALE)
      listenTo(playerItems.selection)
      contents += scrollBar

    }
    panel
  }
}
