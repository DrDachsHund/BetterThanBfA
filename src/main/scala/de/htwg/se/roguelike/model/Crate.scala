package de.htwg.se.roguelike.model

import scala.util.Random

case class Crate(posX:Int = -1,posY:Int = -1,inventory: Inventory) {

  /*
  def fillCrate (crate: Crate): Unit = {
    val random = new Random()
    val itemRandom = random.nextInt(100) + 1
    println(itemRandom)
    itemRandom match {
      case x if 1 until 50 contains x => randomCrate(crate,1)
      case x if 51 until 85 contains x => randomCrate(crate,2) until ist exclusive also wiede bhei 50 weiter
      case x if 86 until 100 contains x => randomCrate(crate,3)
      case _ => print("Fehler beim Crate fill")
    }
  }

  def randomCrate (crate:Crate, items:Int): Unit = {
    val random = new Random()
    val itemRandom = random.nextInt(100) + 1
    for (x <- 1 to items) {
      println(items)
      println(itemRandom)
      itemRandom match { //Maybe einfach gleiche Wahrscheinlichkeit für alle
        case y if 1 until 50 contains y => crate.copy(inventory = inventory.copy(potions = inventory.potions :+ Potion("SmallHeal")))
        case y if 51 until 85 contains y => crate.copy(inventory = inventory.copy(armor = inventory.armor :+ Armor("Sword")))  <= ?????????????????????????
        case y if 86 until 100 contains y => crate.copy(inventory = inventory.copy(weapons = inventory.weapons :+ Weapon("Boots")))  <= ?????????????????????????
        case _ => print("Fehler beim Crate fill")
      }
    }
  }
*/
}
