package de.htwg.se.roguelike.aview

import de.htwg.se.roguelike.controller.Controller
import de.htwg.se.roguelike.controller.GameStatus
import de.htwg.se.roguelike.util.Observer

class Tui(controller: Controller) extends Observer {

  trait State {
    def processInputLine(input:String)
    def handle()
  }

  controller.add(this)
  //State Pattern
  var state:State = new tuiMain

  class tuiMain extends State {
    def processInputLine(input: String): Unit = {
      input match {
        case "q" =>
        case "r" => controller.createRandomLevel
        case "n" => controller.createLevel
        case "w" => controller.moveUp
        case "a" => controller.moveLeft
        case "s" => controller.moveDown
        case "d" => controller.moveRight
        case "z" => controller.undo
        case "y" => controller.redo
        case "i" => controller.gameStatus = GameStatus.INVENTORY
        case _ => {
          print("Wrong Input!!!")
        }
      }
      handle
    }

    override def handle() = {
      val e = controller.gameStatus
      e match {
        case GameStatus.LEVEL => state = this
        case GameStatus.FIGHT => state = new tuiFight
        case GameStatus.INVENTORY => state = new tuiInventoryMain
        case _ => {
          print("Wrong GameStatus!!!")
        }
      }
    }
  }

  class tuiFight extends State {
    def processInputLine(input: String): Unit = {
      input match {
        case "q" =>
        case "1" => controller.attack
        case _ => {
          print("Wrong Input!!!")
        }
      }
      handle
    }

    override def handle() = {
      val e = controller.gameStatus
      e match {
        case GameStatus.LEVEL => state = new tuiMain
        case GameStatus.FIGHT => state = this
        case GameStatus.FIGHTSTATUS => state = this
        case GameStatus.GAMEOVER => println("IS VORBEI MA DUDE")
        case _ => {
          print("Wrong GameStatus!!!")
        }
      }
    }
  }

  class tuiInventoryMain extends State {
    override def processInputLine(input: String): Unit = {
      println("TUIINVENTORY")
      input match {
        case "1" => controller.gameStatus = GameStatus.INVENTORYPOTION
        case "2" => controller.gameStatus = GameStatus.INVENTORYWEAPON
        case "3" => controller.gameStatus = GameStatus.INVENTORYARMOR
        case "4" => //controller.gameStatus = inventoryGamestatus immer setzen wenn inven6ory aufgerufen
        case "q" =>
        case _ => {
          print("Wrong Input!!!")
        }

      }
      handle
    }

    override def handle() = {
      val e = controller.gameStatus
      e match {
        case GameStatus.LEVEL => state = new tuiMain
        case GameStatus.FIGHT => state = this
        case GameStatus.INVENTORYPOTION => state = new tuiInventoryPotion
        case GameStatus.GAMEOVER => println("IS VORBEI MA DUDE")
        case _ => {
          print("Wrong GameStatus!!!")
        }
      }
    }
  }

  class tuiInventoryPotion extends State {
    override def processInputLine(input: String): Unit = {
      println("TUIPOTION")
      input match {
        case "1" => controller.usePotion(input.toInt)
        case "x" => controller.gameStatus = GameStatus.LEVEL
        case _ => {// zahlen überprüfung mit inventory.potion.size
          println("Wrong Input!!!")
        }
      }
      handle
    }
    override def handle() = {
      val e = controller.gameStatus
      e match {
        case GameStatus.LEVEL => state = new tuiMain
        case _ => {
          print("Wrong GameStatus!!!")
        }
      }
    }
  }
  //GameOver Tui fürs REstarten des Games (Fehler bei Attacken nachdem man Stirbt)

  override def update: Unit = println(">> \n" + controller.strategy.updateToString + "<<\n")
}