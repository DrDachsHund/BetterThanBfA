package de.htwg.se.roguelike.model

case class Inventory(weapons: Vector[Weapon] = Vector(), potions: Vector[Potion] = Vector(Potion("SmallHeal"), Potion("SmallHeal"), Potion("BigHeal")), armor: Vector[Armor] = Vector()) {

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

}
