package de.htwg.se.roguelike.model.levelComponent.levelBaseImpl

import scala.util.Random

case class Sword(name: String,
                 value: Int,
                 usable: Boolean,
                 dmg: Int,
                 block: Int,
                 oneHanded: Boolean,
                 rarity: String,
                 itemLevel: Int = 1,
                 textureIndex: Int = 0) extends Weapon {

  private def setItemLevel(lvl: Int): Sword = {
    val random = new Random()
    val randomItemLevel2 = random.nextInt(5)
    var randomItemLevel = 1
    randomItemLevel = lvl + randomItemLevel2
    this.copy(itemLevel = randomItemLevel)
  }


  override def getScaledWeapon(lvl: Int): Weapon = {
    val weapon = setItemLevel(lvl)
    val scale: Double = (weapon.itemLevel.toDouble / 25.0) + 1.0
    weapon.copy(name,
      value = (value * scale).toInt, //Rechnung maybe Falsch, scaling nochmal überschauen/überarbeiten
      usable,
      dmg = (dmg * scale).toInt,
      block = (block * scale).toInt,
      oneHanded, rarity)
  }


}
