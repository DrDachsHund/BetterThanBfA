package de.htwg.se.betterthanbfa.model

case class Player(playerName: String) {
  val name:String = playerName
  var health:Int = 100
  var stamina:Int = 100
  var level:Int = 1
  var experience:Int = 0
  //def setHealth(newHealth:Int): Boolean = (health = newHealth) == newHealth
  //def setStamina(newStamina:Int): Boolean = (stamina = newStamina) == newStamina
  def isAlive: Boolean = health > 0
  override def toString:String = "Name: " + name +
    "\nLevel: " + level +
    "\nHealth: " + health +
    "\nStamina: " + stamina +
    "\nExperience: " + experience
}