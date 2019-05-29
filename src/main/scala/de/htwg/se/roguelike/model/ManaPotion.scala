package de.htwg.se.roguelike.model

case class ManaPotion(name: String,
                      value: Int,
                      usable: Boolean,
                      power: Int,
                      rarity: String) extends Potion {

  override def usePotion(player: Player): Player = {
    super.usePotion(player.copy(mana = player.mana + (player.maxMana / 100 * power)))
  }
}