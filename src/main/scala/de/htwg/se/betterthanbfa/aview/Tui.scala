package de.htwg.se.betterthanbfa.aview

import de.htwg.se.betterthanbfa.controller.{Controller, GameMode}
import de.htwg.se.betterthanbfa.util.Observer

class Tui(controller: Controller) extends Observer {

  controller.add(this)

  def processInputLine(input: String): Unit = {
    if (controller.gameMode == GameMode.Map) {
      input match {
        case "q" =>
        case "n" => controller.createLevel(10)
        case "w" => controller.move(input)
        case "s" => controller.move(input)
        case "a" => controller.move(input)
        case "d" => controller.move(input)
        case "i" =>
          controller.selectInventory()
        case _ => println("FalscheEingabe")
      }
    } else if (controller.gameMode == GameMode.Inventory) {
      //println(controller.playerInventoryToString) maynbe beim update idk
      input match {
        case "q" =>
        case "b" => controller.selectMap()
        case "w" => println(controller.selectWeapon())
        case "s" => println(controller.selectShield())
        case "p" => println(controller.selectPotion())
        case _ => println("FalscheEingabe")
      }
    } else if (controller.gameMode == GameMode.InventoryWeapon) {
      input match {
        case "q" =>
        case "b" => controller.selectInventory()
        case _ =>
          //controler.selectweapon(input)
          println("FalscheEingabe")
      }
    }

  }

  override def update: Boolean = {
    //if gamemode notify
    //jup def mit fight auch so weil das dann mit notify auch bai guy wahrscheinlich gleich funtzt
    println(controller.levelToString);true
  }

}
