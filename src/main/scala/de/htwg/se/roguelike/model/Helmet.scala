package de.htwg.se.roguelike.model

case class Helmet(name: String,
                  value: Int,
                  usable: Boolean,
                  armor: Int,
                  armorType: String = "Helmet",
                  rarity: String) extends Armor {}
