package de.htwg.se.roguelike.model

import scala.util.Random

trait Potion extends Item {
  val name: String
  val value: Int
  val usable: Boolean
  val power: Int

  def usePotion(player: Player): Player = {
    if (player.health > player.maxHealth) {
      return player.copy(health = player.maxHealth)
    }
    if (player.mana > player.maxMana) {
      return player.copy(mana = player.maxMana)
    }
    player
  }

  override def toString: String = name + " (" + rarity + ") PW:" + power + " VAlue: " + value
}

//FactoryMethod
object Potion {
  def apply(kind: String): Potion = kind match {
    case "SmallHeal" => HealPotion(name = "Small-Heal-Potion", value = 25, usable = true, power = 25, rarity = "Common",textureIndex = 4)
    case "MediumHeal" => HealPotion(name = "Medium-Heal-Potion", value = 50, usable = true, power = 50, rarity = "Uncommon",textureIndex = 3)
    case "BigHeal" => HealPotion(name = "Big-Heal-Potion", value = 75, usable = true, power = 75, rarity = "Rare",textureIndex = 2)
    case "FullHeal" => HealPotion(name = "Full-Heal-Potion", value = 100, usable = true, power = 100, rarity = "Epic",textureIndex = 1)
    case "SmallMana" => ManaPotion(name = "Small-Mana-Potion", value = 25, usable = false, power = 25, rarity = "Common",textureIndex = 8) // Power noch nicht final, könnte OP sein
    case "MediumMana" => ManaPotion(name = "Medium-Mana-Potion", value = 50, usable = false, power = 50, rarity = "Uncommon",textureIndex = 7)
    case "BigMana" => ManaPotion(name = "Big-Mana-Potion", value = 75, usable = false, power = 75, rarity = "Rare",textureIndex = 6)
    case "FullMana" => ManaPotion(name = "Full-Mana-Potion", value = 100, usable = false, power = 100, rarity = "Epic",textureIndex = 5)
    case "random" => RandomPotion.getRandomPotion()

  }
}

private object RandomPotion {

  def getRandomPotion(): Potion = {
    val random = Random
    val armorType = random.nextInt(8) + 1
    armorType match {
      case 1 => HealPotion(name = "Small-Heal-Potion", value = 25, usable = true, power = 25, rarity = "Common",textureIndex = 4)
      case 2 => HealPotion(name = "Medium-Heal-Potion", value = 50, usable = true, power = 50, rarity = "Uncommon",textureIndex = 3)
      case 3 => HealPotion(name = "Big-Heal-Potion", value = 75, usable = true, power = 75, rarity = "Rare",textureIndex = 2)
      case 4 => HealPotion(name = "Full-Heal-Potion", value = 100, usable = true, power = 100, rarity = "Epic",textureIndex = 1)
      case 5 => ManaPotion(name = "Small-Mana-Potion", value = 25, usable = true, power = 25, rarity = "Common",textureIndex = 8)
      case 6 => ManaPotion(name = "Medium-Mana-Potion", value = 50, usable = true, power = 50, rarity = "Uncommon",textureIndex = 7)
      case 7 => ManaPotion(name = "Big-Mana-Potion", value = 75, usable = true, power = 75, rarity = "Rare",textureIndex = 6)
      case 8 => ManaPotion(name = "Full-Mana-Potion", value = 100, usable = true, power = 100, rarity = "Epic",textureIndex = 5)
    }
  }


}
