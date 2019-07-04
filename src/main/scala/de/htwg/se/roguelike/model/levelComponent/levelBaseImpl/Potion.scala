package de.htwg.se.roguelike.model.levelComponent.levelBaseImpl

import de.htwg.se.roguelike.model.levelComponent.{ItemInterface, PlayerInterface, PotionInterface}

import scala.util.Random


//FactoryMethod
object Potion {
  def apply(kind: String): PotionInterface = kind match {
    case "SmallHeal" => HealPotion(name = "SmallHeal", value = 25, usable = true, power = 25, rarity = "Common",textureIndex = 4)
    case "MediumHeal" => HealPotion(name = "MediumHeal", value = 50, usable = true, power = 50, rarity = "Uncommon",textureIndex = 3)
    case "BigHeal" => HealPotion(name = "BigHeal", value = 75, usable = true, power = 75, rarity = "Rare",textureIndex = 2)
    case "FullHeal" => HealPotion(name = "FullHeal", value = 100, usable = true, power = 100, rarity = "Epic",textureIndex = 1)
    case "SmallMana" => ManaPotion(name = "SmallMana", value = 25, usable = false, power = 25, rarity = "Common",textureIndex = 8) // Power noch nicht final, könnte OP sein
    case "MediumMana" => ManaPotion(name = "MediumMana", value = 50, usable = false, power = 50, rarity = "Uncommon",textureIndex = 7)
    case "BigMana" => ManaPotion(name = "BigMana", value = 75, usable = false, power = 75, rarity = "Rare",textureIndex = 6)
    case "FullMana" => ManaPotion(name = "FullMana", value = 100, usable = false, power = 100, rarity = "Epic",textureIndex = 5)
    case "random" => RandomPotion.getRandomPotion()
  }
}

private object RandomPotion {

  def getRandomPotion(): PotionInterface = {
    val random = Random
    val armorType = random.nextInt(8) + 1
    armorType match {
      case 1 => HealPotion(name = "SmallHeal", value = 25, usable = true, power = 25, rarity = "Common",textureIndex = 4)
      case 2 => HealPotion(name = "MediumHeal", value = 50, usable = true, power = 50, rarity = "Uncommon",textureIndex = 3)
      case 3 => HealPotion(name = "BigHeal", value = 75, usable = true, power = 75, rarity = "Rare",textureIndex = 2)
      case 4 => HealPotion(name = "FullHeal", value = 100, usable = true, power = 100, rarity = "Epic",textureIndex = 1)
      case 5 => ManaPotion(name = "SmallMana", value = 25, usable = true, power = 25, rarity = "Common",textureIndex = 8)
      case 6 => ManaPotion(name = "MediumMana", value = 50, usable = true, power = 50, rarity = "Uncommon",textureIndex = 7)
      case 7 => ManaPotion(name = "BigMana", value = 75, usable = true, power = 75, rarity = "Rare",textureIndex = 6)
      case 8 => ManaPotion(name = "FullMana", value = 100, usable = true, power = 100, rarity = "Epic",textureIndex = 5)
    }
  }


}
