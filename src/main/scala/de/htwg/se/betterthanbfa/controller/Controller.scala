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

  //def createRandomLevel(size: Int):Unit = {
  //  level.createLevel()
  //}

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
    //level.map(level.player.posY)(level.player.posX) = 1 // ersma am ende so das man sieht wo er steht auch zum zeoichnen vom player why ever
  }

//------------------------------------------------------------------------
  def fightPlayerTurn(input: String):Boolean = {
    level.player.isBlocking = false

    if (fight.fight(level.player,level.currentEnemy)) {

      input match {
        case "0" => fight.attack(level.player, level.currentEnemy) // ersma weil ka wie sonst
        case "1" => fight.block(level.player, level.currentEnemy)
        case "2" => if (!fight.items(level.player, level.currentEnemy)) return false //ändern in game mode = incentory oder so wahrscheinlich zum testen aber ersma fight
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

  //------------------------------------------------------------------------

  def equipWeapon(index: Int): Boolean = {
    if (index >= level.player.inventory.weapons.size) {
      return false
    } else {
      level.player.inventory.addWeapon(level.player.weapon)
      level.player.weapon = level.player.inventory.weapons(index)
      level.player.inventory.removeWeapon(level.player.inventory.weapons(index))
      notifyObservers
      true
    }
  }

  def equipShield(index: Int): Boolean = {
    if (index >= level.player.inventory.shields.size) {
      return false
    } else {
      level.player.inventory.addShields(level.player.shield)
      level.player.shield = level.player.inventory.shields(index)
      level.player.inventory.removeShields(level.player.inventory.shields(index))
      notifyObservers
      true
    }
  }

  def usePotion(input: String): Boolean = {
    var heal = 0
    var heavyHeal = 0
    var mana = 0
    var heavyMana = 0

    heal = level.player.inventory.countPotions("Heal")
    heavyHeal = level.player.inventory.countPotions("HeavyHeal")
    mana = level.player.inventory.countPotions("Mana")
    heavyMana = level.player.inventory.countPotions("HeavyMana")

    var help = ""

      println("[0]Heal(" + heal + ")\n")
      println("[1]HeavyHeal(" + heavyHeal + ")\n")
      println("[2]Mana(" + mana + ")\n")
      println("[3]HeavyMana(" + heavyMana + ")\n")
      println("[x]Belibige Taste zum zurueck gehen")

      input match {
        case "0" => help = "Heal"
          if (heal > 0) test = false
        case "1" => help = "HeavyHeal"
          if (heavyHeal > 0) test = false
        case "2" => help = "Mana"
          if (mana > 0) test = false
        case "3" => help = "HeavyMana"
          if (heavyMana > 0) test = false
        case _ => {
          println("Falsche Eingabe!")
          return false
        }
      }

    for (i <- player.inventory.potions.toList) {
      if (help == i.typ) {
        if (input == "0" || input == "1") {
          player.heal(i.power)
        } else {
          player.refresh(i.power)
        }
        player.inventory.removePotion(i)
        return true
      }
    }

    false
  }


/*
  def fight(input: String): Unit = {
    //pseudo code
    //so könnte man in tui i zulassen bzw eben im fight auswählen und dann einfach in den schon vorhandenen gamemode gehen glabe
    if (fight.playerTurn) {
      if (level.player.isAlive) {
        fight.enemyTurn
      } else {
        fight.loot
        gameMode = GameMode.Map
        //true
      }
    }
    //false
  }

*/

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
