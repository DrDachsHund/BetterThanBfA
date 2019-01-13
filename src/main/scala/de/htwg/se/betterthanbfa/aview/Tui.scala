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
      input match {
        case "q" =>
        case "b" => controller.selectMap()
        case "w" => println(controller.selectWeapon)
        case "s" => println(controller.selectShield)
        case "p" => println(controller.selectPotion)
        case _ => println("FalscheEingabe")
      }
    } else if (controller.gameMode == GameMode.InventoryWeapon) {
      input match {
        case "q" =>
        case "b" => controller.selectInventory()
        case _ =>
          var x: Int = 0
          try {
            x = input.toInt
          } catch {
            case e: Exception => 0
          }
          if(!controller.equipWeapon(x)) {
            println("FalscheEingabe")
            //println("x:" + x)
          }
      }
    } else if (controller.gameMode == GameMode.InventoryShield) {
      input match {
        case "q" =>
        case "b" => controller.selectInventory()
        case _ =>
          var x: Int = 0
          try {
            x = input.toInt
          } catch {
            case e: Exception => 0
          }
          if(!controller.equipShield(x)) {
            println("FalscheEingabe")
            //println("x:" + x)
          }
      }
    } else if (controller.gameMode == GameMode.InventoryPotion) {
      input match {
        case "q" =>
        case "b" => controller.selectInventory()
        case _ =>
          if (!controller.usePotion(input)) {
            println("FalscheEingabe oder Keine Potion zu verfügung")
          }
      }
    } else if (controller.gameMode == GameMode.FightPlayerTurn) {
      input match {
        case "q" =>
        case _ =>
          if (!controller.fightPlayerTurn(input))
            println("FalscheEingabe")
      }
    } else if (controller.gameMode == GameMode.Loot) {
      input match {
        case "q" =>
        case _ =>
          if (!controller.lootEnemy(input))
            println("FalscheEingabe")
      }
    }

  }

  override def update: Boolean = {
    if (controller.gameMode == GameMode.Map) {
      println("GameMode: " + controller.gameMode)
      println(controller.levelToString);
      return true
    } else if (controller.gameMode == GameMode.Inventory) {
      println("GameMode: " + controller.gameMode)
      println(controller.inventoryToString);
      return true
    } else if (controller.gameMode == GameMode.InventoryWeapon) {
      println("GameMode: " + controller.gameMode)
      println(controller.weaponsToString);
      return true
    } else if (controller.gameMode == GameMode.InventoryShield) {
      println("GameMode: " + controller.gameMode)
      println(controller.shieldsToString);
      return true
    } else if (controller.gameMode == GameMode.InventoryPotion) {
      println("GameMode: " + controller.gameMode)
      println(controller.potionsToString);
      return true
    } else if (controller.gameMode == GameMode.Fight) {
      println("GameMode: " + controller.gameMode)
      println(controller.fightToString)
      return true
    } else if (controller.gameMode == GameMode.FightPlayerTurn) {
      println("GameMode: " + controller.gameMode)
      println(controller.playerTurnToString)
      return true
    } else  if (controller.gameMode == GameMode.FightEnemyTurn) {
      println("GameMode: " + controller.gameMode)
      println(controller.enemyTurnToString)
      return true
    } else  if (controller.gameMode == GameMode.Loot) {
      println("GameMode: " + controller.gameMode)
      println(controller.lootEnemyToString)
      return true
    }

    false
  }

}
