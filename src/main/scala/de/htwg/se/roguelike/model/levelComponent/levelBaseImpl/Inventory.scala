package de.htwg.se.roguelike.model.levelComponent.levelBaseImpl

import de.htwg.se.roguelike.model.levelComponent.InventoryInterface

case class Inventory(weapons: Vector[Weapon] = Vector(), potions: Vector[Potion] = Vector(Potion("SmallHeal"), Potion("SmallHeal"), Potion("BigHeal")), armor: Vector[Armor] = Vector()) extends InventoryInterface {

  def invSortPower(): Inventory = {
    val newWeapons = weapons.sortWith(_.dmg > _.dmg)
    val newArmor = armor.sortWith(_.armor > _.armor)
    val newPotion = potions.sortWith(_.power > _.power)
    this.copy(weapons = newWeapons, potions = newPotion, armor = newArmor)
  }

  def invSortValue(): Inventory = {
    val newWeapons = weapons.sortWith(_.value > _.value)
    val newArmor = armor.sortWith(_.value > _.value)
    val newPotion = potions.sortWith(_.value > _.value)
    this.copy(weapons = newWeapons, potions = newPotion, armor = newArmor)
  }

  def getPotion(index: Int): Potion = {
    potions(index - 1)
  }

  def getArmor(index: Int): Armor = {
    armor(index - 1)
  }

  def getWeapon(index: Int): Weapon = {
    weapons(index - 1)
  }

  def potionsToString(): String = {
    if (potions.isEmpty) return "Keine Potions\n"
    var sb = new StringBuilder
    var index: Int = 1
    for (e <- potions) {
      sb ++= "[" + index + "]" + e.toString + "\n"
      index = index + 1
    }
    sb.toString
  }

  def weaponsToString(): String = {
    if (weapons.isEmpty) return "Keine Weapons\n"
    var sb = new StringBuilder
    var index: Int = 1
    for (e <- weapons) {
      sb ++= "[" + index + "]" + e.toString + "\n"
      index = index + 1
    }
    sb.toString
  }

  def armorToString(): String = {
    if (armor.isEmpty) return "Keine Armor\n"
    var sb = new StringBuilder
    var index: Int = 1
    for (e <- armor) {
      sb ++= "[" + index + "]" + e.toString + "\n"
      index = index + 1
    }
    sb.toString
  }

  def nextInventory(weapons: Vector[Weapon] = this.weapons,
                    potions: Vector[Potion] = this.potions,
                    armor: Vector[Armor] = this.armor): InventoryInterface = {
    this.copy(weapons = weapons, potions = potions, armor = armor)
  }

}
