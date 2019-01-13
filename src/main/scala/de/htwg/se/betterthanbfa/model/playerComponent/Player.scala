package de.htwg.se.betterthanbfa.model.playerComponent

import de.htwg.se.betterthanbfa.model.ItemComponent.{Inventory, Potion, Shield, Weapon}

case class Player(playerName: String) {
  val entityN: Int = 1
  val name: String = playerName
  var attack: Int = 10
  var health: Int = 100
  var maxHealth: Int = 100
  var mana: Int = 100
  var maxMana: Int = 100
  var level: Int = 1
  var experience: Int = 0
  var maxExperience: Int = 100
  var posX: Int = 0
  var posY: Int = 0

  var isBlocking = false

  val inventory: Inventory = new Inventory()

  //nur zum testen
  private var TESTPOTION = new Potion()
  TESTPOTION.randomPotion(this)
  inventory.addPotion(TESTPOTION)

  var shield: Shield = new Shield()
  var weapon: Weapon = new Weapon()

  def isAlive: Boolean = health > 0

  def heal(heal: Int): Unit = {
    health += heal
    if (health > maxHealth) {
      health = maxHealth
    }
  }
  def refresh(power: Int): Unit = {
    mana += power
    if (mana > maxMana) {
      mana = maxMana
    }
  }

  def collectExperience(experience: Int): Boolean = {
    this.experience += experience
    if (this.experience > maxExperience) {
      level += 1
      this.experience -= maxExperience
      maxExperience = maxExperience * level
      maxHealth += 10
      maxMana += 5
      health = maxHealth
      mana = maxMana
      return true
    }
    return false
  }

  def equipWeapon(index: Int): Boolean = {
    if (index >= inventory.weapons.size) {
      return false
    } else {
      inventory.addWeapon(weapon)
      weapon = inventory.weapons(index)
      inventory.removeWeapon(inventory.weapons(index))
      true
    }
  }

  def equipShield(index: Int): Boolean = {
    if (index >= inventory.shields.size) {
      return false
    } else {
      inventory.addShields(shield)
      shield = inventory.shields(index)
      inventory.removeShields(inventory.shields(index))
      true
    }
  }

  def usePotion(input: String): Boolean = {
    var help: String = ""

    input match {
      case "0" =>
        if (inventory.countPotions("Heal") > 0) {
          help = "Heal"
        } else {
          return false
        }
      case "1" =>
        if (inventory.countPotions("HeavyHeal") > 0) {
          help = "HeavyHeal"
        } else {
          return false
        }
      case "2" =>
        if (inventory.countPotions("Mana") > 0) {
          help = "Mana"
        } else {
          return false
        }
      case "3" =>
        if (inventory.countPotions("HeavyMana") > 0) {
          help = "HeavyMana"
        } else {
          return false
        }
      case _ => {
        return false
      }
    }

    for (i <- inventory.potions.toList) {
      if (help == i.typ) {
        if (input == "0" || input == "1") {
          heal(i.power)
        } else {
          refresh(i.power)
        }
        inventory.removePotion(i)
        return true
      }
    }

    false
  }


  override def toString: String = "Name: " + name +
    "\nLevel: " + level +
    "\nHealth: " + health +
    "\nMana: " + mana +
    "\nExperience: " + experience +
    "\n" + weapon.toString +
    "\n" + shield.toString +
    "\n" + inventory.toString

}