package de.htwg.se.roguelike.model

trait Entity {
  val name: String
  val health:Int
  val attack:Int
  val lvl:Int
  val exp:Int
  val posX:Int
  val posY:Int
  val inventory:Vector[Item]
  val rightHand:Weapon
  val leftHand:Weapon

  def isAlive():Boolean = health > 0

  def getAttack():Int = attack + rightHand.dmg + (leftHand.dmg / 2)


}
