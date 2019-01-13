package de.htwg.se.betterthanbfa.model.ItemComponent

import de.htwg.se.betterthanbfa.model.playerComponent.Player

class Weapon() {

  val r = new scala.util.Random

  var name: String = "Right-Hand"

  var value: Int = 0
  var attack: Int = 0

  def randomWeapon(player: Player):Unit = {
    value = player.level + r.nextInt(10)
    attack = player.level + r.nextInt(5)
    //name nur so zum testen!!!
    name = name + player.level + value + attack
  }

  override def toString: String = "WeaponName: " + name +
    "\nWeaponValue: " + value +
    "\nWeaponAttack: " + attack

}
