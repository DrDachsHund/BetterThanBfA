package de.htwg.se.betterthanbfa.model.playerComponent

case class Player(playerName: String) {
  val entityN: Int = 1
  val name: String = playerName
  var health: Int = 100
  var mana: Int = 100
  var level: Int = 1
  var experience: Int = 0
  var posX: Int = 0
  var posY: Int = 0


  def isAlive: Boolean = health > 0

  def moveRight() = posX += 1

  def moveLeft() = posX -= 1

  def moveUp() = posY += 1

  def moveDown() = posY -= 1


  override def toString: String = "Name: " + name +
    "\nLevel: " + level +
    "\nHealth: " + health +
    "\nMana: " + mana +
    "\nExperience: " + experience
}