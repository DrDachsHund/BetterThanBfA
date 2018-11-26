package de.htwg.se.betterthanbfa.model

case class Enemy(player: Player) {
  val r = new scala.util.Random

  var health:Int = 100
  var mana:Int = 100
  var posX:Int = 0
  var posY:Int = 0
  var level:Int = setLevel()
  var name = Array("Zombie","Skeleton","Human","Bandit","Skeleton-Mage")
  var typ:String = setType()

  def setType() = name(r.nextInt(5))

  def isAlive: Boolean = health > 0

  def setLevel() = {
    var tmp = player.level + (3 - r.nextInt(7))
    if (tmp < 1) tmp = player.level
    tmp
  }

  override def toString:String = "Typ: " + typ +
    "\nLevel: " + level +
    "\nHealth: " + health +
    "\nMana: " + mana
}
