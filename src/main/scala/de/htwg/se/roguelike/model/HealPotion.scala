package de.htwg.se.roguelike.model

case class HealPotion(name: String,
                      value: Int,
                      usable: Boolean,
                      power: Int,
                      rarity: String) extends Potion {

  override def usePotion(player: Player): Player = {
    super.usePotion(player.copy(health = player.health + (player.maxHealth / 100 * power)))
  }

  override def toString: String = {
    rarity + " " + name + " Value: " + value + "Power: " + power
  }
}
