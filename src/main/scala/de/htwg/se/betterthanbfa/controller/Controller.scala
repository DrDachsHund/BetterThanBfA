package de.htwg.se.betterthanbfa.controller

import de.htwg.se.betterthanbfa.model.CombatComponent.Fight
import de.htwg.se.betterthanbfa.model.LevelComponent.Level
import de.htwg.se.betterthanbfa.util.Observable

class Controller(level: Level) extends Observable{

  val fight: Fight = new Fight

  var gameMode = GameMode.Map

  def createLevel(size: Int):Unit = {
    level.createLevel(size)
    notifyObservers
  }

  def move(move: String):Unit = {
    move match {
      case "w" => level.moveUp()
      case "s" => level.moveDown()
      case "a" => level.moveLeft()
      case "d" => level.moveRight()
    }
    isInFight()
    level.map(level.player.posY)(level.player.posX) = 1 // ersma am ende so das man sieht wo er steht auch zum zeoichnen vom player why ever maybe nicht hier idk
    notifyObservers
  }

  private def isInFight(): Unit = {
    for (x <- level.enemys) {
      if (x.isAlive) {
        if (x.posX == level.player.posX && x.posY == level.player.posY) {
          //fight.fight(level.player,x)
          level.currentEnemy = x
          gameMode = GameMode.Fight
          notifyObservers
          gameMode = GameMode.FightPlayerTurn
        }
      }
    }
  }

  def fightPlayerTurn(input: String):Boolean = {
    //maybe if input === 2 mach invent sont methode in fight
    level.player.isBlocking = false

    if (fight.fight(level.player,level.currentEnemy)) {

      input match {
        case "0" => fight.attack(level.player, level.currentEnemy) // ersma weil ka wie sonst
        case "1" => fight.block(level.player, level.currentEnemy)
        case "2" =>
          gameMode = GameMode.Inventory
          notifyObservers
          return true
        case "3" => fight.flee(level.player, level.currentEnemy)
        case _ => {
          notifyObservers
          return false
        }
      }
      if (fight.fight(level.player,level.currentEnemy)) {
        gameMode = GameMode.FightEnemyTurn
        notifyObservers // weis noch nicht wie ich des mit observern mach ersma in fight die ausgaben
        level.currentEnemy.isBlocking = false
        fight.enemyTurn(level.player,level.currentEnemy)
        gameMode = GameMode.FightPlayerTurn
        notifyObservers
        return true
      } else {
        fight.collectEXP(level.player,level.currentEnemy)
        gameMode = GameMode.Loot
        notifyObservers
        return true
      }
    }
    gameMode = GameMode.Map
    notifyObservers
    true
  }

  def lootEnemy(input: String): Boolean = {
    fight.lootEnemy(level.player,level.currentEnemy,input)
    gameMode = GameMode.Map
    notifyObservers
    true
  }

  def equipWeapon(index: Int): Boolean = {
    if (level.player.equipWeapon(index)) {
      notifyObservers
      return true
    }
    false
  }

  def equipShield(index: Int): Boolean = {
    if (level.player.equipShield(index)) {
      notifyObservers
      return true
    }
    false
  }

  def usePotion(input: String): Boolean = {
    if (level.player.usePotion(input)) {
      notifyObservers
      return true
    }
    false
  }

  def selectMap(): Unit = {
    gameMode = GameMode.Map
    notifyObservers
  }

  def selectInventory(): Unit = {
    gameMode = GameMode.Inventory
    notifyObservers
  }

  def selectWeapon(): Unit = {
    gameMode = GameMode.InventoryWeapon
    notifyObservers
  }

  def selectShield(): Unit = {
    gameMode = GameMode.InventoryShield
    notifyObservers
  }

  def selectPotion(): Unit = {
    gameMode = GameMode.InventoryPotion
    notifyObservers
  }

  def levelToString: String = level.toString
  def playerInventoryToString: String = level.player.inventory.toString
  def inventoryToString: String = "Current Weapon: " + level.player.weapon.toString + "\nCurrent Shield: " + level.player.shield.toString
  def weaponsToString: String = level.player.inventory.weaponstoString
  def shieldsToString: String = level.player.inventory.shieldstoString
  def potionsToString: String = level.player.inventory.potionstoString
  def fightToString: String = fight.fightToString(level.player,level.currentEnemy)
  def playerTurnToString: String = fight.playerTurnToString(level.player,level.currentEnemy)
  def enemyTurnToString: String = fight.enemyTurnToString(level.player,level.currentEnemy)
  def lootEnemyToString: String = fight.lootEnemyToString(level.currentEnemy)

}


object GameMode extends Enumeration {
  type GameMode = Value
  val Map, Loot, Fight, FightEnemyTurn, FightPlayerTurn, Inventory, InventoryWeapon, InventoryShield, InventoryPotion = Value // maybe nur fight
}
