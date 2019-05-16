package de.htwg.se.roguelike.model

trait Item {
  val name:String
  val value:Int
  val usable:Boolean

  def isUseable: Boolean = usable
}
