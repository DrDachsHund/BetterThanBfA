package de.htwg.se.roguelike.model

trait Item {
  val name: String
  val value: Int
  val usable: Boolean
  val rarity: String

  def isUseable: Boolean = usable

  //def randomItem
}
