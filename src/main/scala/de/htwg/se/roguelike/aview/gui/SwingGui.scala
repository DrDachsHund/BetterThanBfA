package de.htwg.se.roguelike.aview.gui

import java.awt.event.{KeyEvent, KeyListener}
import de.htwg.se.roguelike.controller.{Controller, FightEvent, LevelSizeChanged, TileChanged}
import scala.swing._
import scala.swing.event.Key


class SwingGui(controller: Controller) extends Reactor {

  listenTo(controller)

  //---GUI--

  val frame = new MainFrame()
  val SCALE = 5 // eig in controller but for testing here

  var state:StateGui = new guiMain(controller, this)

  frame.title = "Pog Game"
  setSize

  //frame.peer.requestFocus() //noch testen
/*
  frame.contents = new BorderPanel {
    add(new GPanelLevel(controller, SCALE), BorderPanel.Position.Center)
  }
*/
  frame.contents = state.drawPanel(SCALE)
  frame.menuBar = new GMenuBar(controller)

  frame.peer.setResizable(false)
  frame.peer.setLocationRelativeTo(null)
  frame.visible = true

  //--GUI--


  reactions += {
    case _: TileChanged => redraw
    case _: LevelSizeChanged => redraw
    case _: FightEvent => redraw
  }

  def redraw(): Unit = {
    //zeugs
    state.handle()
    frame.contents = state.drawPanel(SCALE)
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
      state.processInputLine(e.getKeyChar.toString)
    }

    def keyReleased(e: KeyEvent) {
      //println("key released")
    }

    def keyTyped(e: KeyEvent) {
      //println("key typed")
    }
  })


  //MouseListener


}


private class GMenuBar(controller: Controller) extends MenuBar {
  this.peer.setSize(new Dimension(0, 20))
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
      controller.undo()
    })
    contents += new MenuItem(Action("Redo") {
      controller.redo()
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




