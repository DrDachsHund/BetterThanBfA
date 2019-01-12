package de.htwg.se.betterthanbfa.model.ItemComponent

import de.htwg.se.betterthanbfa.model.playerComponent.Player

class Shield() {
  val r = new scala.util.Random

  var name: String = "TESTSchild"

  var value: Int = 0
  var block: Int = 0

  def randomShield(player: Player):Unit = {
    value = player.level + r.nextInt(10)
    block = player.level + r.nextInt(5)
    name = name + player.level + value + block
  }

  override def toString: String = "ShieldName: " + name +
    "\nShieldValue: " + value +
    "\nShieldBlock: " + block

}
