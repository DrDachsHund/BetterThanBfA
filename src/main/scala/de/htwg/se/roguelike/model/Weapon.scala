package de.htwg.se.roguelike.model

trait Weapon extends Item {
  val name: String
  val value: Int
  val usable: Boolean
  val dmg: Int
  val block: Int
  val oneHanded:Boolean

  /*
  def getAttack():(Int,Int,Int) = { //normalDmg,magicDmg,critDmg/trueDmg,etc

  }

  def special():(Int,Int,Int) = {

  }
  */
}



object Weapon {
  def apply(kind: String) = kind match {
    case "rightFist" => new Sword(name = "RightFist", value = 0, usable = false, dmg = 5, block = 5,oneHanded = true)
    case "leftFist" => new Sword(name = "LeftFist", value = 0, usable = false, dmg = 5, block = 5, oneHanded = true)
    case "Sword" => new Sword(name = "Sword", value = 10, usable = false, dmg = 10, block = 5, oneHanded = true)
  }
}
