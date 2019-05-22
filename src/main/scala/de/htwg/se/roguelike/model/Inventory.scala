package de.htwg.se.roguelike.model

case class Inventory(weapons:Vector[Weapon] = Vector(),potions:Vector[Potion] = Vector(Potion("Heal")),armor:Vector[Armor] = Vector()) {

  def getPotion(index:Int): Potion = {
    potions(index-1)
  }

}
