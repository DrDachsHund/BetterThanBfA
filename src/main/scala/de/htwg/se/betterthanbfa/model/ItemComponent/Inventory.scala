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

  def countPotions(name: String): Int = {
    var x = 0
    for (i <- potions.toList) {
      if (i.typ == name) x += 1
    }
    x
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
    var x: Int = 0
    for (i <- weapons.toList) {
      s += "(" + x + ")" + i + "\n"
      x += 1
    }
    s
  }
  def shieldstoString: String = {
    var s: String = "Shields: \n"
    var x: Int = 0
    for (i <- shields.toList) {
      s += "(" + x + ")" + i + "\n"
      x += 1
    }
    s
  }

  def potionstoString: String = {
    var heal = 0
    var heavyHeal = 0
    var mana = 0
    var heavyMana = 0

    heal = countPotions("Heal")
    heavyHeal = countPotions("HeavyHeal")
    mana = countPotions("Mana")
    heavyMana = countPotions("HeavyMana")

    var s = ""

    s += "[0]Heal(" + heal + ")\n"
    s += "[1]HeavyHeal(" + heavyHeal + ")\n"
    s += "[2]Mana(" + mana + ")\n"
    s += "[3]HeavyMana(" + heavyMana + ")\n"
    s += "[b]Zum zurueck gehen"
    s
  }


}
