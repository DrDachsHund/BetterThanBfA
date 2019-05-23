package de.htwg.se.roguelike.model

trait Entity {
  val name: String
  val health:Int
  val attack:Int
  val lvl:Int
  val exp:Int
  val posX:Int
  val posY:Int
  val inventory:Inventory
  val rightHand:Weapon
  val leftHand:Weapon

  def isAlive():Boolean = health > 0

  //Template Method
  def getAttack():Double = attack + rightHand.dmg + (leftHand.dmg / 2)

}
