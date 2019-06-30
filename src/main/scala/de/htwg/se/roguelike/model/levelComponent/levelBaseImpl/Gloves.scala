package de.htwg.se.roguelike.model.levelComponent.levelBaseImpl

import de.htwg.se.roguelike.model.levelComponent.ArmorInterface

case class Gloves(name: String,
                  value: Int,
                  usable: Boolean,
                  armor: Int,
                  armorType: String = "Gloves",
                  rarity: String,
                  textureIndex:Int = 0) extends ArmorInterface {}
