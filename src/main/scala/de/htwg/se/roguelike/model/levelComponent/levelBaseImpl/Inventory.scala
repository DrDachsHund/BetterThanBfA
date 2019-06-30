package de.htwg.se.roguelike.model.levelComponent.levelBaseImpl

import de.htwg.se.roguelike.model.levelComponent.{ArmorInterface, InventoryInterface, PotionInterface, WeaponInterface}

case class Inventory(weapons: Vector[WeaponInterface] = Vector(), potions: Vector[PotionInterface] = Vector(Potion("SmallHeal"), Potion("SmallHeal"), Potion("BigHeal")), armor: Vector[ArmorInterface] = Vector()) extends InventoryInterface {

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

  def getPotion(index: Int): PotionInterface = {
    potions(index - 1)
  }

  def getArmor(index: Int): ArmorInterface = {
    armor(index - 1)
  }

  def getWeapon(index: Int): WeaponInterface = {
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

  def nextInventory(weapons: Vector[WeaponInterface] = this.weapons,
                    potions: Vector[PotionInterface] = this.potions,
                    armor: Vector[ArmorInterface] = this.armor): InventoryInterface = {
    this.copy(weapons = weapons, potions = potions, armor = armor)
  }

}
