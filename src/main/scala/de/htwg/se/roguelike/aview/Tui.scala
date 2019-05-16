package de.htwg.se.roguelike.aview

import de.htwg.se.roguelike.controller.Controller
import de.htwg.se.roguelike.controller.GameStatus
import de.htwg.se.roguelike.util.Observer

class Tui(controller: Controller) extends Observer {

  trait State {
    def processInputLine(input:String)
    def handle(e: GameStatus.gameStatus )
  }

  controller.add(this)

  var state:State = new tuiMain

  class tuiMain extends State {

    def processInputLine(input: String): Unit = {
      input match {
        case "q" =>
        case "r" => controller.createRandomLevel
        case "n" => controller.createLevel

        case "w" => handle(controller.moveUp)
        case "a" => handle(controller.moveLeft)
        case "s" => handle(controller.moveDown)
        case "d" => handle(controller.moveRight)

        case _ => {
          print("Wrong Input!!!")
        }
      }
    }

    override def handle(e: GameStatus.gameStatus ) = {
      e match {
        case GameStatus.LEVEL => state = this
        case GameStatus.FIGHT => state = new tuiFight
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
        case "1" => handle(controller.attack)
        case _ => {
          print("Wrong Input!!!")
        }
      }
    }

    override def handle(e: GameStatus.gameStatus ) = {
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

  override def update: Unit = println(">> \n" + controller.updateToString + "<<\n")



}