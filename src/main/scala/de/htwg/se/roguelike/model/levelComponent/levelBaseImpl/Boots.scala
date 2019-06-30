package de.htwg.se.roguelike.model.levelComponent.levelBaseImpl

import de.htwg.se.roguelike.model.levelComponent.ArmorInterface

case class Boots(name: String,
                 value: Int,
                 usable: Boolean,
                 armor: Int,
                 armorType: String = "Boots",
                 rarity: String,
                 textureIndex:Int = 0) extends ArmorInterface {}
