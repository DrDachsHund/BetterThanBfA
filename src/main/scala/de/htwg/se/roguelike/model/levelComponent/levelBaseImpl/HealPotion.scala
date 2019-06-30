package de.htwg.se.roguelike.model.levelComponent.levelBaseImpl

import de.htwg.se.roguelike.model.levelComponent.{PlayerInterface, PotionInterface}

case class HealPotion(name: String,
                      value: Int,
                      usable: Boolean,
                      power: Int,
                      rarity: String,
                      textureIndex:Int = 0) extends PotionInterface {

  override def usePotion(player: PlayerInterface): PlayerInterface = {
    super.usePotion(player.nextPlayer(health = player.health + (player.maxHealth / 100 * power)))
  }
}
