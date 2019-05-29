package de.htwg.se.roguelike.model

case class Gloves(name:String,
                 value: Int,
                 usable: Boolean,
                 armor:Int,
                  armorType:String = "Gloves",
                  rarity: String) extends Armor {}


