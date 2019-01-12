package de.htwg.se.betterthanbfa.model.ItemComponent

import scala.collection.mutable.ListBuffer

class Inventory {

  var weapons: ListBuffer[Weapon] = ListBuffer()
  var shields: ListBuffer[Shield] = ListBuffer()
  var potions: ListBuffer[Potion] = ListBuffer()

  def addWeapon(weapon: Weapon): Unit = {
    weapons += weapon
  }

  def addShields(shield: Shield): Unit = {
    shields += shield
  }

  def addPotion(potion: Potion): Unit = {
    potions += potion
  }

  def removeWeapon(weapon: Weapon): Unit = {
    weapons -= weapon
  }

  def removeShields(shield: Shield): Unit = {
    shields -= shield
  }

  def removePotion(potion: Potion): Unit = {
    potions -= potion
  }

  override def toString: String = {
    var s: String = "Inventory: \n"
    s += "Wepons:"
    for (i <- weapons.toList) {
      s += i + "\n"
    }
    s += "\nShield:"
    for (i <- shields.toList) {
      s += i + "\n"
    }
    s += "\nPotion:"
    for (i <- potions.toList) {
      s += i + "\n"
    }
    s
  }

  def weaponstoString: String = {
    var s: String = "Weapons: \n"
    for (i <- weapons.toList) {
      s += i + "\n"
    }
    s
  }
  def shieldstoString: String = {
    var s: String = "Shields: \n"
    for (i <- shields.toList) {
      s += i + "\n"
    }
    s
  }
  def potionstoString: String = {
    var s: String = "Potions: \n"
    for (i <- potions.toList) {
      s += i + "\n"
    }
    s
  }

}
