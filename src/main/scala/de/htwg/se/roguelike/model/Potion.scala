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
  def apply(kind: String) = kind match {
    case "Heal" => new HealPotion("HealPotion",10,false,50) //.randomItem
    case "Mana" => new HealPotion("ManaPotion",10,false,50)
  }
}
