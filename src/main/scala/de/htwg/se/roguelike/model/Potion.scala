package de.htwg.se.roguelike.model

import scala.util.Random

trait Potion extends Item {
  val name:String
  val value: Int
  val usable: Boolean
  val power:Int

  def usePotion(player: Player):Player = {
    if (player.health > player.maxHealth) {
      return player.copy(health = player.maxHealth)
    }
    player
  }
}

//FactoryMethod
object Potion {
  def apply(kind: String):Potion = kind match {
    case "SmallHeal" => HealPotion(name = "Small-Heal-Potion", value = 25,usable = true, power = 25,rarity = "Common") //.raroíty ändern
    case "MediumHeal" => HealPotion(name = "Medium-Heal-Potion", value = 50,usable = true, power = 50,rarity = "Common")
    case "BigHeal" => HealPotion(name = "Big-Heal-Potion", value = 75,usable = true, power = 75,rarity = "Common")
    case "FullHeal" => HealPotion(name = "Full-Heal-Potion",value = 100,usable = true, power = 100,rarity = "Common")
    //case "Mana" => new ManaPotion("ManaPotion",10,false,50)
    case "random" => RandomPotion.getRandomPotion()

  }
}

private object RandomPotion {

  def getRandomPotion(): Potion = {
    val random = Random //geht auch so zu schreiben lul wierd
    val armorType = random.nextInt(4) + 1
    armorType match {
      case 1 => HealPotion(name = "Small-Heal-Potion", value = 25,usable = true, power = 25,rarity = "Common") //.randomItem
      case 2 => HealPotion(name = "Medium-Heal-Potion", value = 50,usable = true, power = 50,rarity = "Common")
      case 3 => HealPotion(name = "Big-Heal-Potion", value = 75,usable = true, power = 75,rarity = "Common")
      case 4 => HealPotion(name = "Full-Heal-Potion",value = 100,usable = true, power = 100,rarity = "Common")
    }
  }




}
