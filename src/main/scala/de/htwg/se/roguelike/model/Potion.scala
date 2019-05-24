package de.htwg.se.roguelike.model

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
    case "SmallHeal" => HealPotion(name = "Small-Heal-Potion", value = 10,usable = true, power = 25) //.randomItem
    case "BigHeal" => HealPotion(name = "Big-Heal-Potion", value = 10,usable = true, power = 50)
    //case "Mana" => new ManaPotion("ManaPotion",10,false,50)
  }
}
