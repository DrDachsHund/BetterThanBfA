package de.htwg.se.roguelike.model

case class Inventory(weapons:Vector[Weapon] = Vector(),potions:Vector[Potion] = Vector(Potion("Heal")),armor:Vector[Armor] = Vector()) {

  def getPotion(index:Int): Potion = {
    potions(index-1)
  }

  def potionsToString():String = {
    if (potions.size == 0) return "Keine Potions\n"
    var sb = new StringBuilder
    var index:Int = 1
    for (e <- potions) {
      sb ++= "[" + index + "]" + e.name + "\n"
      index = index+1
    }
    sb.toString
  }

  def weaponsToString():String = {
    if (weapons.size == 0) return "Keine Weapons\n"
    var sb = new StringBuilder
    var index:Int = 1
    for (e <- weapons) {
      sb ++= "[" + index + "]" + e.name + "\n"
      index = index+1
    }
    sb.toString
  }

  def armorToString():String = {
    if (armor.size == 0) return "Keine Armor\n"
    var sb = new StringBuilder
    var index:Int = 1
    for (e <- armor) {
      sb ++= "[" + index + "]" + e.name + "\n"
      index = index+1
    }
    sb.toString
  }

}
