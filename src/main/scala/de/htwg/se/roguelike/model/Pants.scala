package de.htwg.se.roguelike.model

case class Pants(name: String,
                 value: Int,
                 usable: Boolean,
                 armor: Int,
                 armorType: String = "Pants",
                 rarity: String,
                 textureIndex:Int = 0) extends Armor {}