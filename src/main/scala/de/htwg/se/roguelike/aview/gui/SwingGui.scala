package de.htwg.se.roguelike.aview.gui

import java.awt.event.{KeyEvent, KeyListener}

import de.htwg.se.roguelike.controller._

import scala.swing._
import scala.swing.event.Key


class SwingGui(controller: Controller) extends Reactor {

  listenTo(controller)

  //---GUI--

  val frame = new MainFrame()
  //var SCALE = 3 // jetzt in controller

  var state: StateGui = new guiMain(controller, this)

  frame.title = "Pog Game"
  setSize

  /*
    frame.contents = new BorderPanel {
      add(new GPanelLevel(controller, SCALE), BorderPanel.Position.Center)
    }
  */
  frame.contents = state.drawPanel(controller.SCALE)
  frame.menuBar = new GMenuBar(controller)

  frame.peer.setResizable(false)
  frame.peer.setLocationRelativeTo(null)
  frame.visible = true
  frame.peer.requestFocus() //noch testen

  //--GUI--


  reactions += {
    case _: TileChanged => redraw
    case _: LevelSizeChanged => redraw
    case _: FightEvent => redraw
    case _: ResolutionEvent => resize
  }

  def resize(): Unit = {
    setSize()
    redraw()
    frame.peer.setLocationRelativeTo(null)
    frame.peer.requestFocus()
  }

  def redraw(): Unit = {
    //zeugs
    state.handle()
    frame.contents = state.drawPanel(controller.SCALE)
    frame.repaint
    frame.peer.requestFocus()
  }

  def closeOperation() {
    System.exit(0)
  }

  def setSize(): Unit = {
    val width = 256 * controller.SCALE
    val height = 144 * controller.SCALE + 20
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
    contents += new Menu("Resolution") {
      contents += new MenuItem(Action("256x164") {controller.setSCALE(1)})
      contents += new MenuItem(Action("512x308") {controller.setSCALE(2)})
      contents += new MenuItem(Action("768x452") {controller.setSCALE(3)})
      contents += new MenuItem(Action("1024x596") {controller.setSCALE(4)})
      contents += new MenuItem(Action("1280x740") {controller.setSCALE(5)})
      contents += new MenuItem(Action("1536x884") {controller.setSCALE(6)})
      contents += new MenuItem(Action("1792x1028") {controller.setSCALE(7)})
      contents += new MenuItem(Action("2048x1172") {controller.setSCALE(8)})
    }
    contents += new MenuItem(Action("OPTIONS") {})
    contents += new MenuItem(Action("LUL") {})
    contents += new MenuItem(Action("W") {})
  }
}




