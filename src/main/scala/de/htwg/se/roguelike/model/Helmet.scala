package de.htwg.se.roguelike.model

case class Helmet(name: String,
                  value: Int,
                  usable: Boolean,
                  armor: Int,
                  armorType: String = "Helmet",
                  rarity: String,
                  textureIndex:Int = 0) extends Armor {}
