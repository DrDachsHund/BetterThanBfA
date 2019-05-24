package de.htwg.se.roguelike.model

case class HealPotion(name:String,
                      value: Int,
                      usable: Boolean,
                      power:Int) extends Potion {

  override def usePotion(player: Player): Player = {
    super.usePotion(player.copy(health = player.health + power))
  }
}
