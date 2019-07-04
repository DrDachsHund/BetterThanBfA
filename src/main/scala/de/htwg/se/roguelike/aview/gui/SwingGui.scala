package de.htwg.se.roguelike.aview.gui

import java.awt.event.{KeyEvent, KeyListener}

import scala.swing._
import scala.swing.event.Key
import de.htwg.se.roguelike.controller._
import de.htwg.se.roguelike.controller.controllerBaseImpl.Controller


class SwingGui(controller: ControllerInterface) extends Reactor {

  listenTo(controller)

  //---GUI--

  val frame = new MainFrame()
  //var SCALE = 3 // jetzt in controller

  var state: StateGui = new guiStartScreen(controller, this)

  frame.title = "Rogue-Like"
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

  //playSound()

  //--GUI--


  reactions += {
    case _: TileChanged => redraw
    case _: LevelSizeChanged => redraw
    case _: ResolutionEvent => resize
    case _: RepaintEvent => frame.repaint()
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
    val height = 144 * controller.SCALE
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

}


private class GMenuBar(controller: ControllerInterface) extends MenuBar {
  this.peer.setSize(new Dimension(0, 20))
  contents += new Menu("File") {
    mnemonic = Key.F //Dose not work
    contents += new MenuItem(Action("New") {
      controller.newGame()
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
      contents += new MenuItem(Action("256x144") {controller.setSCALE(1)})
      contents += new MenuItem(Action("512x288") {controller.setSCALE(2)})
      contents += new MenuItem(Action("768x432") {controller.setSCALE(3)})
      contents += new MenuItem(Action("1024x576") {controller.setSCALE(4)})
      contents += new MenuItem(Action("1280x720") {controller.setSCALE(5)})
      contents += new MenuItem(Action("1536x864") {controller.setSCALE(6)})
      contents += new MenuItem(Action("1792x1008") {controller.setSCALE(7)})
      contents += new MenuItem(Action("2048x1152") {controller.setSCALE(8)})
    }
    contents += new MenuItem(Action("Save Game") {controller.save()})
    contents += new MenuItem(Action("Load Game") {controller.load()})
  }
  /*
  contents += new MenuItem(Action("Inventory") {
    controller.setGameStatus(GameStatus.INVENTORY)
  })
  */
}




