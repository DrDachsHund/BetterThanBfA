package de.htwg.se.roguelike.aview

import de.htwg.se.roguelike.controller.Controller
import de.htwg.se.roguelike.util.Observer

class Tui(controller: Controller) extends Observer {

  trait Strategy {
    def tui(input:String)
  }

  controller.add(this)

  var test:Int = 0

  var strategy = if (test == 0) new tuiMain else new tuiFight

  class tuiMain extends Strategy {
    def tui(input: String): Unit = {
      input match {
        case "q" =>
        case "r" => controller.createRandomLevel
        case "n" => controller.createLevel

        case "i" =>
          val interaction = controller.interaction
          if (interaction) {
            println("Player interacts with Enemy")
            test = 1
            strategy = new tuiFight
          }
          else println("No interaction found")

        case "w" => controller.moveUp
        case "a" => controller.moveLeft
        case "s" => controller.moveDown
        case "d" => controller.moveRight
        case _ => {
          print("Wrong Input!!!")
        }
      }
    }
  }

  class tuiFight extends Strategy {
    def tui(input: String): Unit = {
      input match {
        case "q" =>
        case "x" => println("DXXDXDXXXD")
        case _ => {
          print("Wrong Input!!!")
        }
      }
    }
  }

  override def update: Unit = println(controller.levelToString)



}