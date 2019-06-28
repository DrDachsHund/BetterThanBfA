package de.htwg.se.roguelike.model.levelComponent.levelBaseImpl

trait Item {
  val name: String
  val value: Int
  val usable: Boolean
  val rarity: String
  val textureIndex : Int

  def isUseable: Boolean = usable

  //def randomItem
}
